package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class Doctor {
    /*医生工号*/
    @NotEmpty(message="医生工号不能为空")
    private String doctorNo;
    public String getDoctorNo(){
        return doctorNo;
    }
    public void setDoctorNo(String doctorNo){
        this.doctorNo = doctorNo;
    }

    /*登录密码*/
    @NotEmpty(message="登录密码不能为空")
    private String password;
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    /*姓名*/
    @NotEmpty(message="姓名不能为空")
    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    /*性别*/
    @NotEmpty(message="性别不能为空")
    private String gender;
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }

    /*出生日期*/
    @NotEmpty(message="出生日期不能为空")
    private String birthDate;
    public String getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    /*医生照片*/
    private String doctorPhoto;
    public String getDoctorPhoto() {
        return doctorPhoto;
    }
    public void setDoctorPhoto(String doctorPhoto) {
        this.doctorPhoto = doctorPhoto;
    }

    /*职称*/
    @NotEmpty(message="职称不能为空")
    private String zc;
    public String getZc() {
        return zc;
    }
    public void setZc(String zc) {
        this.zc = zc;
    }

    /*联系电话*/
    @NotEmpty(message="联系电话不能为空")
    private String telephone;
    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /*工作经验*/
    @NotEmpty(message="工作经验不能为空")
    private String workYear;
    public String getWorkYear() {
        return workYear;
    }
    public void setWorkYear(String workYear) {
        this.workYear = workYear;
    }

    /*医生介绍*/
    private String doctorDesc;
    public String getDoctorDesc() {
        return doctorDesc;
    }
    public void setDoctorDesc(String doctorDesc) {
        this.doctorDesc = doctorDesc;
    }

    /*入职时间*/
    private String regTime;
    public String getRegTime() {
        return regTime;
    }
    public void setRegTime(String regTime) {
        this.regTime = regTime;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonDoctor=new JSONObject(); 
		jsonDoctor.accumulate("doctorNo", this.getDoctorNo());
		jsonDoctor.accumulate("password", this.getPassword());
		jsonDoctor.accumulate("name", this.getName());
		jsonDoctor.accumulate("gender", this.getGender());
		jsonDoctor.accumulate("birthDate", this.getBirthDate().length()>19?this.getBirthDate().substring(0,19):this.getBirthDate());
		jsonDoctor.accumulate("doctorPhoto", this.getDoctorPhoto());
		jsonDoctor.accumulate("zc", this.getZc());
		jsonDoctor.accumulate("telephone", this.getTelephone());
		jsonDoctor.accumulate("workYear", this.getWorkYear());
		jsonDoctor.accumulate("doctorDesc", this.getDoctorDesc());
		jsonDoctor.accumulate("regTime", this.getRegTime().length()>19?this.getRegTime().substring(0,19):this.getRegTime());
		return jsonDoctor;
    }}