package com.chengxusheji.service;

import java.util.ArrayList;
import javax.annotation.Resource; 
import org.springframework.stereotype.Service;

import com.chengxusheji.po.Admin;
import com.chengxusheji.po.Doctor;

import com.chengxusheji.mapper.DoctorMapper;
@Service
public class DoctorService {

	@Resource DoctorMapper doctorMapper;
    /*每页显示记录数目*/
    private int rows = 10;;
    public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}

    /*保存查询后总的页数*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*保存查询到的总记录数*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*添加医生记录*/
    public void addDoctor(Doctor doctor) throws Exception {
    	doctorMapper.addDoctor(doctor);
    }

    /*按照查询条件分页查询医生记录*/
    public ArrayList<Doctor> queryDoctor(String doctorNo,String name,String birthDate,String zc,String telephone,String regTime,int currentPage) throws Exception { 
     	String where = "where 1=1";
    	if(!doctorNo.equals("")) where = where + " and t_doctor.doctorNo like '%" + doctorNo + "%'";
    	if(!name.equals("")) where = where + " and t_doctor.name like '%" + name + "%'";
    	if(!birthDate.equals("")) where = where + " and t_doctor.birthDate like '%" + birthDate + "%'";
    	if(!zc.equals("")) where = where + " and t_doctor.zc like '%" + zc + "%'";
    	if(!telephone.equals("")) where = where + " and t_doctor.telephone like '%" + telephone + "%'";
    	if(!regTime.equals("")) where = where + " and t_doctor.regTime like '%" + regTime + "%'";
    	int startIndex = (currentPage-1) * this.rows;
    	return doctorMapper.queryDoctor(where, startIndex, this.rows);
    }

    /*按照查询条件查询所有记录*/
    public ArrayList<Doctor> queryDoctor(String doctorNo,String name,String birthDate,String zc,String telephone,String regTime) throws Exception  { 
     	String where = "where 1=1";
    	if(!doctorNo.equals("")) where = where + " and t_doctor.doctorNo like '%" + doctorNo + "%'";
    	if(!name.equals("")) where = where + " and t_doctor.name like '%" + name + "%'";
    	if(!birthDate.equals("")) where = where + " and t_doctor.birthDate like '%" + birthDate + "%'";
    	if(!zc.equals("")) where = where + " and t_doctor.zc like '%" + zc + "%'";
    	if(!telephone.equals("")) where = where + " and t_doctor.telephone like '%" + telephone + "%'";
    	if(!regTime.equals("")) where = where + " and t_doctor.regTime like '%" + regTime + "%'";
    	return doctorMapper.queryDoctorList(where);
    }

    /*查询所有医生记录*/
    public ArrayList<Doctor> queryAllDoctor()  throws Exception {
        return doctorMapper.queryDoctorList("where 1=1");
    }

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber(String doctorNo,String name,String birthDate,String zc,String telephone,String regTime) throws Exception {
     	String where = "where 1=1";
    	if(!doctorNo.equals("")) where = where + " and t_doctor.doctorNo like '%" + doctorNo + "%'";
    	if(!name.equals("")) where = where + " and t_doctor.name like '%" + name + "%'";
    	if(!birthDate.equals("")) where = where + " and t_doctor.birthDate like '%" + birthDate + "%'";
    	if(!zc.equals("")) where = where + " and t_doctor.zc like '%" + zc + "%'";
    	if(!telephone.equals("")) where = where + " and t_doctor.telephone like '%" + telephone + "%'";
    	if(!regTime.equals("")) where = where + " and t_doctor.regTime like '%" + regTime + "%'";
        recordNumber = doctorMapper.queryDoctorCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取医生记录*/
    public Doctor getDoctor(String doctorNo) throws Exception  {
        Doctor doctor = doctorMapper.getDoctor(doctorNo);
        return doctor;
    }

    /*更新医生记录*/
    public void updateDoctor(Doctor doctor) throws Exception {
        doctorMapper.updateDoctor(doctor);
    }

    /*删除一条医生记录*/
    public void deleteDoctor (String doctorNo) throws Exception {
        doctorMapper.deleteDoctor(doctorNo);
    }

    /*删除多条医生信息*/
    public int deleteDoctors (String doctorNos) throws Exception {
    	String _doctorNos[] = doctorNos.split(",");
    	for(String _doctorNo: _doctorNos) {
    		doctorMapper.deleteDoctor(_doctorNo);
    	}
    	return _doctorNos.length;
    }
	 
	
	
	/*保存业务逻辑错误信息字段*/
	private String errMessage;
	public String getErrMessage() { return this.errMessage; }
	
	/*验证用户登录*/
	public boolean checkLogin(Admin admin) throws Exception { 
		Doctor db_doctor = (Doctor) doctorMapper.getDoctor(admin.getUsername());
		if(db_doctor == null) { 
			this.errMessage = " 账号不存在 ";
			System.out.print(this.errMessage);
			return false;
		} else if( !db_doctor.getPassword().equals(admin.getPassword())) {
			this.errMessage = " 密码不正确! ";
			System.out.print(this.errMessage);
			return false;
		}
		
		return true;
	}
	
	
}
