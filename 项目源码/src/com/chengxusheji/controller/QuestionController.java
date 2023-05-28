package com.chengxusheji.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.chengxusheji.utils.ExportExcelUtil;
import com.chengxusheji.utils.UserException;
import com.chengxusheji.service.QuestionService;
import com.chengxusheji.service.ReplyService;
import com.chengxusheji.po.Question;
import com.chengxusheji.po.Reply;
import com.chengxusheji.service.QuestionClassService;
import com.chengxusheji.po.QuestionClass;
import com.chengxusheji.service.UserInfoService;
import com.chengxusheji.po.UserInfo;

//Question管理控制层
@Controller
@RequestMapping("/Question")
public class QuestionController extends BaseController {

    /*业务层对象*/
    @Resource QuestionService questionService;
    @Resource ReplyService replyService;

    @Resource QuestionClassService questionClassService;
    @Resource UserInfoService userInfoService;
	@InitBinder("questionClassObj")
	public void initBinderquestionClassObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("questionClassObj.");
	}
	@InitBinder("userObj")
	public void initBinderuserObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("userObj.");
	}
	@InitBinder("question")
	public void initBinderQuestion(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("question.");
	}
	/*跳转到添加Question视图*/
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model,HttpServletRequest request) throws Exception {
		model.addAttribute(new Question());
		/*查询所有的QuestionClass信息*/
		List<QuestionClass> questionClassList = questionClassService.queryAllQuestionClass();
		request.setAttribute("questionClassList", questionClassList);
		/*查询所有的UserInfo信息*/
		List<UserInfo> userInfoList = userInfoService.queryAllUserInfo();
		request.setAttribute("userInfoList", userInfoList);
		return "Question_add";
	}

	/*客户端ajax方式提交添加问题信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(@Validated Question question, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
		boolean success = false;
		if (br.hasErrors()) {
			message = "输入信息不符合要求！";
			writeJsonResponse(response, success, message);
			return ;
		}
        questionService.addQuestion(question);
        message = "问题添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	
	
	/*客户端ajax方式提交添加问题信息*/
	@RequestMapping(value = "/userAdd", method = RequestMethod.POST)
	public void userAdd(Question question, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response,HttpSession session) throws Exception {
		String message = "";
		boolean success = false;
		if (br.hasErrors()) {
			message = "输入信息不符合要求！";
			writeJsonResponse(response, success, message);
			return ;
		}
		
		UserInfo userInfo = new UserInfo();
		userInfo.setUser_name(session.getAttribute("user_name").toString());
		question.setUserObj(userInfo);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		question.setAddTime(sdf.format(new java.util.Date()));
		
        questionService.addQuestion(question);
        message = "问题发布成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	
	
	
	
	/*ajax方式按照查询条件分页查询问题信息*/
	@RequestMapping(value = { "/list" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void list(String title,@ModelAttribute("questionClassObj") QuestionClass questionClassObj,@ModelAttribute("userObj") UserInfo userObj,String addTime,Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		if (page==null || page == 0) page = 1;
		if (title == null) title = "";
		if (addTime == null) addTime = "";
		if(rows != 0)questionService.setRows(rows);
		List<Question> questionList = questionService.queryQuestion(title, questionClassObj, userObj, addTime, page);
	    /*计算总的页数和总的记录数*/
	    questionService.queryTotalPageAndRecordNumber(title, questionClassObj, userObj, addTime);
	    /*获取到总的页码数目*/
	    int totalPage = questionService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = questionService.getRecordNumber();
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONObject jsonObj=new JSONObject();
		jsonObj.accumulate("total", recordNumber);
		JSONArray jsonArray = new JSONArray();
		for(Question question:questionList) {
			JSONObject jsonQuestion = question.getJsonObject();
			jsonArray.put(jsonQuestion);
		}
		jsonObj.accumulate("rows", jsonArray);
		out.println(jsonObj.toString());
		out.flush();
		out.close();
	}

	/*ajax方式按照查询条件分页查询问题信息*/
	@RequestMapping(value = { "/listAll" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void listAll(HttpServletResponse response) throws Exception {
		List<Question> questionList = questionService.queryAllQuestion();
        response.setContentType("text/json;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		JSONArray jsonArray = new JSONArray();
		for(Question question:questionList) {
			JSONObject jsonQuestion = new JSONObject();
			jsonQuestion.accumulate("postInfoId", question.getPostInfoId());
			jsonQuestion.accumulate("title", question.getTitle());
			jsonArray.put(jsonQuestion);
		}
		out.println(jsonArray.toString());
		out.flush();
		out.close();
	}

	/*前台按照查询条件分页查询问题信息*/
	@RequestMapping(value = { "/frontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String frontlist(String title,@ModelAttribute("questionClassObj") QuestionClass questionClassObj,@ModelAttribute("userObj") UserInfo userObj,String addTime,Integer currentPage, Model model, HttpServletRequest request) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (title == null) title = "";
		if (addTime == null) addTime = "";
		List<Question> questionList = questionService.queryQuestion(title, questionClassObj, userObj, addTime, currentPage);
	    /*计算总的页数和总的记录数*/
	    questionService.queryTotalPageAndRecordNumber(title, questionClassObj, userObj, addTime);
	    /*获取到总的页码数目*/
	    int totalPage = questionService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = questionService.getRecordNumber();
	    request.setAttribute("questionList",  questionList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("title", title);
	    request.setAttribute("questionClassObj", questionClassObj);
	    request.setAttribute("userObj", userObj);
	    request.setAttribute("addTime", addTime);
	    List<QuestionClass> questionClassList = questionClassService.queryAllQuestionClass();
	    request.setAttribute("questionClassList", questionClassList);
	    List<UserInfo> userInfoList = userInfoService.queryAllUserInfo();
	    request.setAttribute("userInfoList", userInfoList);
		return "Question/question_frontquery_result"; 
	}
	
	
	/*前台按照查询条件分页查询问题信息*/
	@RequestMapping(value = { "/frontUserlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String frontUserlist(String title,@ModelAttribute("questionClassObj") QuestionClass questionClassObj,@ModelAttribute("userObj") UserInfo userObj,String addTime,Integer currentPage, Model model, HttpServletRequest request,HttpSession session) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (title == null) title = "";
		if (addTime == null) addTime = "";
		
		userObj = new UserInfo();
		userObj.setUser_name(session.getAttribute("user_name").toString());
		List<Question> questionList = questionService.queryQuestion(title, questionClassObj, userObj, addTime, currentPage);
	    /*计算总的页数和总的记录数*/
	    questionService.queryTotalPageAndRecordNumber(title, questionClassObj, userObj, addTime);
	    /*获取到总的页码数目*/
	    int totalPage = questionService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = questionService.getRecordNumber();
	    request.setAttribute("questionList",  questionList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("title", title);
	    request.setAttribute("questionClassObj", questionClassObj);
	    request.setAttribute("userObj", userObj);
	    request.setAttribute("addTime", addTime);
	    List<QuestionClass> questionClassList = questionClassService.queryAllQuestionClass();
	    request.setAttribute("questionClassList", questionClassList);
	    List<UserInfo> userInfoList = userInfoService.queryAllUserInfo();
	    request.setAttribute("userInfoList", userInfoList);
		return "Question/question_frontUserquery_result"; 
	}
	

     /*前台查询Question信息*/
	@RequestMapping(value="/{postInfoId}/frontshow",method=RequestMethod.GET)
	public String frontshow(@PathVariable Integer postInfoId,Model model,HttpServletRequest request) throws Exception {
		/*根据主键postInfoId获取Question对象*/
        Question question = questionService.getQuestion(postInfoId);
        question.setHitNum(question.getHitNum() + 1);
        questionService.updateQuestion(question);
        List<Reply> replyList = replyService.queryReply(question, null, ""); 
        List<QuestionClass> questionClassList = questionClassService.queryAllQuestionClass();
        request.setAttribute("questionClassList", questionClassList);
        List<UserInfo> userInfoList = userInfoService.queryAllUserInfo();
        request.setAttribute("userInfoList", userInfoList);
        request.setAttribute("question",  question);
        request.setAttribute("replyList", replyList);
        return "Question/question_frontshow";
	}

	/*ajax方式显示问题修改jsp视图页*/
	@RequestMapping(value="/{postInfoId}/update",method=RequestMethod.GET)
	public void update(@PathVariable Integer postInfoId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键postInfoId获取Question对象*/
        Question question = questionService.getQuestion(postInfoId);

        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象 
		JSONObject jsonQuestion = question.getJsonObject();
		out.println(jsonQuestion.toString());
		out.flush();
		out.close();
	}

	/*ajax方式更新问题信息*/
	@RequestMapping(value = "/{postInfoId}/update", method = RequestMethod.POST)
	public void update(@Validated Question question, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
    	boolean success = false;
		if (br.hasErrors()) { 
			message = "输入的信息有错误！";
			writeJsonResponse(response, success, message);
			return;
		}
		try {
			questionService.updateQuestion(question);
			message = "问题更新成功!";
			success = true;
			writeJsonResponse(response, success, message);
		} catch (Exception e) {
			e.printStackTrace();
			message = "问题更新失败!";
			writeJsonResponse(response, success, message); 
		}
	}
    /*删除问题信息*/
	@RequestMapping(value="/{postInfoId}/delete",method=RequestMethod.GET)
	public String delete(@PathVariable Integer postInfoId,HttpServletRequest request) throws UnsupportedEncodingException {
		  try {
			  questionService.deleteQuestion(postInfoId);
	            request.setAttribute("message", "问题删除成功!");
	            return "message";
	        } catch (Exception e) { 
	            e.printStackTrace();
	            request.setAttribute("error", "问题删除失败!");
				return "error";

	        }

	}

	/*ajax方式删除多条问题记录*/
	@RequestMapping(value="/deletes",method=RequestMethod.POST)
	public void delete(String postInfoIds,HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		String message = "";
    	boolean success = false;
        try { 
        	int count = questionService.deleteQuestions(postInfoIds);
        	success = true;
        	message = count + "条记录删除成功";
        	writeJsonResponse(response, success, message);
        } catch (Exception e) { 
            //e.printStackTrace();
            message = "有记录存在外键约束,删除失败";
            writeJsonResponse(response, success, message);
        }
	}

	/*按照查询条件导出问题信息到Excel*/
	@RequestMapping(value = { "/OutToExcel" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void OutToExcel(String title,@ModelAttribute("questionClassObj") QuestionClass questionClassObj,@ModelAttribute("userObj") UserInfo userObj,String addTime, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
        if(title == null) title = "";
        if(addTime == null) addTime = "";
        List<Question> questionList = questionService.queryQuestion(title,questionClassObj,userObj,addTime);
        ExportExcelUtil ex = new ExportExcelUtil();
        String _title = "Question信息记录"; 
        String[] headers = { "帖子id","问题标题","问题分类","浏览量","提问人","提问时间"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<questionList.size();i++) {
        	Question question = questionList.get(i); 
        	dataset.add(new String[]{question.getPostInfoId() + "",question.getTitle(),question.getQuestionClassObj().getClassName(),question.getHitNum() + "",question.getUserObj().getName(),question.getAddTime()});
        }
        /*
        OutputStream out = null;
		try {
			out = new FileOutputStream("C://output.xls");
			ex.exportExcel(title,headers, dataset, out);
		    out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		OutputStream out = null;//创建一个输出流对象 
		try { 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"Question.xls");//filename是下载的xls的名，建议最好用英文 
			response.setContentType("application/msexcel;charset=UTF-8");//设置类型 
			response.setHeader("Pragma","No-cache");//设置头 
			response.setHeader("Cache-Control","no-cache");//设置头 
			response.setDateHeader("Expires", 0);//设置日期头  
			String rootPath = request.getSession().getServletContext().getRealPath("/");
			ex.exportExcel(rootPath,_title,headers, dataset, out);
			out.flush();
		} catch (IOException e) { 
			e.printStackTrace(); 
		}finally{
			try{
				if(out!=null){ 
					out.close(); 
				}
			}catch(IOException e){ 
				e.printStackTrace(); 
			} 
		}
    }
}
