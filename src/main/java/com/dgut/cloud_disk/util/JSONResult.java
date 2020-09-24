package com.dgut.cloud_disk.util;

<<<<<<< HEAD
<<<<<<< HEAD
import com.fasterxml.jackson.annotation.JsonIgnore;

=======
>>>>>>> zhurongxin
=======
>>>>>>> lzh
/**
 * @Description: 自定义响应数据结构 这个类是提供给门户，ios，安卓，微信商城用的
 *               门户接受此类数据后需要使用本类的方法转换成对于的数据类型格式（类，或者list） 其他自行处理 200：表示成功
 *               500：表示错误，错误信息在msg字段中 
 *               502：拦截器拦截到用户token出错 555：异常抛出信息 401：请求未授权 400：请求参数不支持
 */
public class JSONResult {

	// 响应业务状态
	private Integer status;

	// 响应消息
	private String msg;

	// 响应中的数据
	private Object data;


	public static JSONResult build(Integer status, String msg, Object data) {
		return new JSONResult(status, msg, data);
	}

<<<<<<< HEAD
	public static JSONResult ok(Object data) {
		return new JSONResult(data);
	}

	public static JSONResult ok() {
		return new JSONResult(null);
	}

=======
>>>>>>> lzh
	public static JSONResult errorMsg(String msg) {
		return new JSONResult(500, msg, null);
	}


	public static JSONResult errorTokenMsg(String msg) {
		return new JSONResult(502, msg, null);
	}

	public static JSONResult errorException(String msg) {
		return new JSONResult(555, msg, null);
	}

	public static JSONResult errorAuthorized(String msg) {
		return new JSONResult(401, msg, null);
	}

	public static JSONResult errorMethodArgument(String msg, Object data) {
		return new JSONResult(400, msg, data);
	}

	public JSONResult() {

	}

	public JSONResult(Integer status, String msg, Object data) {
		this.status = status;
		this.msg = msg;
		this.data = data;
	}

	public JSONResult(Object data) {
		this.status = 200;
<<<<<<< HEAD
<<<<<<< HEAD
		this.msg = "OK";
=======
		this.msg = "";
>>>>>>> zhurongxin
		this.data = data;
	}

	public Boolean isOK() {
		return this.status == 200;
	}

=======
		this.msg = "OK";
		this.data = data;
	}

>>>>>>> lzh
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

<<<<<<< HEAD
<<<<<<< HEAD
	/*public String getOk() {
		return ok;
	}

	public void setOk(String ok) {
		this.ok = ok;
	}*/

=======
>>>>>>> zhurongxin
=======
>>>>>>> lzh
}
