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
import com.chengxusheji.service.DoctorService;
import com.chengxusheji.po.Doctor;

//Doctor管理控制层
@Controller
@RequestMapping("/Doctor")
public class DoctorController extends BaseController {

    /*业务层对象*/
    @Resource DoctorService doctorService;

	@InitBinder("doctor")
	public void initBinderDoctor(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("doctor.");
	}
	/*跳转到添加Doctor视图*/
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model,HttpServletRequest request) throws Exception {
		model.addAttribute(new Doctor());
		return "Doctor_add";
	}

	/*客户端ajax方式提交添加医生信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(@Validated Doctor doctor, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
		boolean success = false;
		if (br.hasErrors()) {
			message = "输入信息不符合要求！";
			writeJsonResponse(response, success, message);
			return ;
		}
		if(doctorService.getDoctor(doctor.getDoctorNo()) != null) {
			message = "医生工号已经存在！";
			writeJsonResponse(response, success, message);
			return ;
		}
		try {
			doctor.setDoctorPhoto(this.handlePhotoUpload(request, "doctorPhotoFile"));
		} catch(UserException ex) {
			message = "图片格式不正确！";
			writeJsonResponse(response, success, message);
			return ;
		}
        doctorService.addDoctor(doctor);
        message = "医生添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	/*ajax方式按照查询条件分页查询医生信息*/
	@RequestMapping(value = { "/list" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void list(String doctorNo,String name,String birthDate,String zc,String telephone,String regTime,Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		if (page==null || page == 0) page = 1;
		if (doctorNo == null) doctorNo = "";
		if (name == null) name = "";
		if (birthDate == null) birthDate = "";
		if (zc == null) zc = "";
		if (telephone == null) telephone = "";
		if (regTime == null) regTime = "";
		if(rows != 0)doctorService.setRows(rows);
		List<Doctor> doctorList = doctorService.queryDoctor(doctorNo, name, birthDate, zc, telephone, regTime, page);
	    /*计算总的页数和总的记录数*/
	    doctorService.queryTotalPageAndRecordNumber(doctorNo, name, birthDate, zc, telephone, regTime);
	    /*获取到总的页码数目*/
	    int totalPage = doctorService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = doctorService.getRecordNumber();
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONObject jsonObj=new JSONObject();
		jsonObj.accumulate("total", recordNumber);
		JSONArray jsonArray = new JSONArray();
		for(Doctor doctor:doctorList) {
			JSONObject jsonDoctor = doctor.getJsonObject();
			jsonArray.put(jsonDoctor);
		}
		jsonObj.accumulate("rows", jsonArray);
		out.println(jsonObj.toString());
		out.flush();
		out.close();
	}

	/*ajax方式按照查询条件分页查询医生信息*/
	@RequestMapping(value = { "/listAll" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void listAll(HttpServletResponse response) throws Exception {
		List<Doctor> doctorList = doctorService.queryAllDoctor();
        response.setContentType("text/json;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		JSONArray jsonArray = new JSONArray();
		for(Doctor doctor:doctorList) {
			JSONObject jsonDoctor = new JSONObject();
			jsonDoctor.accumulate("doctorNo", doctor.getDoctorNo());
			jsonDoctor.accumulate("name", doctor.getName());
			jsonArray.put(jsonDoctor);
		}
		out.println(jsonArray.toString());
		out.flush();
		out.close();
	}

	/*前台按照查询条件分页查询医生信息*/
	@RequestMapping(value = { "/frontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String frontlist(String doctorNo,String name,String birthDate,String zc,String telephone,String regTime,Integer currentPage, Model model, HttpServletRequest request) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (doctorNo == null) doctorNo = "";
		if (name == null) name = "";
		if (birthDate == null) birthDate = "";
		if (zc == null) zc = "";
		if (telephone == null) telephone = "";
		if (regTime == null) regTime = "";
		List<Doctor> doctorList = doctorService.queryDoctor(doctorNo, name, birthDate, zc, telephone, regTime, currentPage);
	    /*计算总的页数和总的记录数*/
	    doctorService.queryTotalPageAndRecordNumber(doctorNo, name, birthDate, zc, telephone, regTime);
	    /*获取到总的页码数目*/
	    int totalPage = doctorService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = doctorService.getRecordNumber();
	    request.setAttribute("doctorList",  doctorList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("doctorNo", doctorNo);
	    request.setAttribute("name", name);
	    request.setAttribute("birthDate", birthDate);
	    request.setAttribute("zc", zc);
	    request.setAttribute("telephone", telephone);
	    request.setAttribute("regTime", regTime);
		return "Doctor/doctor_frontquery_result"; 
	}

     /*前台查询Doctor信息*/
	@RequestMapping(value="/{doctorNo}/frontshow",method=RequestMethod.GET)
	public String frontshow(@PathVariable String doctorNo,Model model,HttpServletRequest request) throws Exception {
		/*根据主键doctorNo获取Doctor对象*/
        Doctor doctor = doctorService.getDoctor(doctorNo);

        request.setAttribute("doctor",  doctor);
        return "Doctor/doctor_frontshow";
	}

	/*ajax方式显示医生修改jsp视图页*/
	@RequestMapping(value="/{doctorNo}/update",method=RequestMethod.GET)
	public void update(@PathVariable String doctorNo,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键doctorNo获取Doctor对象*/
        Doctor doctor = doctorService.getDoctor(doctorNo);

        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象 
		JSONObject jsonDoctor = doctor.getJsonObject();
		out.println(jsonDoctor.toString());
		out.flush();
		out.close();
	}

	/*ajax方式更新医生信息*/
	@RequestMapping(value = "/{doctorNo}/update", method = RequestMethod.POST)
	public void update(@Validated Doctor doctor, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
    	boolean success = false;
		if (br.hasErrors()) { 
			message = "输入的信息有错误！";
			writeJsonResponse(response, success, message);
			return;
		}
		String doctorPhotoFileName = this.handlePhotoUpload(request, "doctorPhotoFile");
		if(!doctorPhotoFileName.equals("upload/NoImage.jpg"))doctor.setDoctorPhoto(doctorPhotoFileName); 


		try {
			doctorService.updateDoctor(doctor);
			message = "医生更新成功!";
			success = true;
			writeJsonResponse(response, success, message);
		} catch (Exception e) {
			e.printStackTrace();
			message = "医生更新失败!";
			writeJsonResponse(response, success, message); 
		}
	}
    /*删除医生信息*/
	@RequestMapping(value="/{doctorNo}/delete",method=RequestMethod.GET)
	public String delete(@PathVariable String doctorNo,HttpServletRequest request) throws UnsupportedEncodingException {
		  try {
			  doctorService.deleteDoctor(doctorNo);
	            request.setAttribute("message", "医生删除成功!");
	            return "message";
	        } catch (Exception e) { 
	            e.printStackTrace();
	            request.setAttribute("error", "医生删除失败!");
				return "error";

	        }

	}

	/*ajax方式删除多条医生记录*/
	@RequestMapping(value="/deletes",method=RequestMethod.POST)
	public void delete(String doctorNos,HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		String message = "";
    	boolean success = false;
        try { 
        	int count = doctorService.deleteDoctors(doctorNos);
        	success = true;
        	message = count + "条记录删除成功";
        	writeJsonResponse(response, success, message);
        } catch (Exception e) { 
            //e.printStackTrace();
            message = "有记录存在外键约束,删除失败";
            writeJsonResponse(response, success, message);
        }
	}

	/*按照查询条件导出医生信息到Excel*/
	@RequestMapping(value = { "/OutToExcel" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void OutToExcel(String doctorNo,String name,String birthDate,String zc,String telephone,String regTime, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
        if(doctorNo == null) doctorNo = "";
        if(name == null) name = "";
        if(birthDate == null) birthDate = "";
        if(zc == null) zc = "";
        if(telephone == null) telephone = "";
        if(regTime == null) regTime = "";
        List<Doctor> doctorList = doctorService.queryDoctor(doctorNo,name,birthDate,zc,telephone,regTime);
        ExportExcelUtil ex = new ExportExcelUtil();
        String _title = "Doctor信息记录"; 
        String[] headers = { "医生工号","姓名","性别","出生日期","医生照片","职称","联系电话","工作经验","入职时间"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<doctorList.size();i++) {
        	Doctor doctor = doctorList.get(i); 
        	dataset.add(new String[]{doctor.getDoctorNo(),doctor.getName(),doctor.getGender(),doctor.getBirthDate(),doctor.getDoctorPhoto(),doctor.getZc(),doctor.getTelephone(),doctor.getWorkYear(),doctor.getRegTime()});
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
			response.setHeader("Content-disposition","attachment; filename="+"Doctor.xls");//filename是下载的xls的名，建议最好用英文 
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
