package com.github.crud.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.github.crud.bean.EmployeeModel;
import com.github.crud.common.Msg;
import com.github.crud.service.EmployeeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
/**
 * Handle crud request
 * @author williamfan
 * pengfei.fan@hotmail.com
 */
@Controller
public class EmployeeController {

	private static final Logger logger= LoggerFactory.getLogger(EmployeeController.class);
	
	@Autowired
	EmployeeService employeeService;

	/**
	 * 查询员工数据(分页查询)
	 * 导入jackson包
	 * @param pageNo
	 * @return
	 */
	@RequestMapping("/listEmps")
	@ResponseBody
	public Msg getEmpsWithJson(
			@RequestParam(value="pageNo",defaultValue="1")Integer pageNo){
		// 引入PageHelper分页插件
		// 在查询之前只需要调用，传入页码，以及每页的大小
		// 从pageNo开始,每页显示十条数据
		PageHelper.startPage(pageNo,10);
		// startPage后面紧跟的这个查询就是一个分页查询
		List<EmployeeModel> emps = employeeService.getAll();
		// 使用pageInfo包装查询后的结果，只需要将pageInfo交给页面就行了。
		// 封装了详细的分页信息,包括有我们查询出来的数据，传入连续显示的页数
		PageInfo page=new PageInfo(emps,5);
		logger.debug("listEmps pageInfo:",JSON.toJSONString(page));
		return Msg.success().add("pageInfo", page);
	}
	/**
	 * 单个批量二合一
	 * 批量删除：1-2-3
	 * 单个删除：1
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/emp/{ids}",method=RequestMethod.DELETE)
	public Msg deleteEmpById(@PathVariable("ids")String ids){
		// 批量删除
		if(ids.contains("-")){
			List<Integer> del_ids = new ArrayList<>();
			String[] str_ids = ids.split("-");
			// 组装id的集合
			for(String str:str_ids){
				del_ids.add(Integer.parseInt(str));
			}
			employeeService.batchDel(del_ids);
		}else{
			Integer id = Integer.parseInt(ids);
			employeeService.delEmp(id);
		}
		return Msg.success();
	}
	/**
	 * 要能支持直接发送PUT之类的请求还要封装请求体中的数据
	 * 1、配置上HttpPutFormContentFilter；
	 * 2、他的作用；将请求体中的数据解析包装成一个map。
	 * 3、request被重新包装，request.getParameter()被重写，就会从自己封装的map中取数据
	 * 员工更新
	 * @param employee
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/emp/{empId}",method=RequestMethod.PUT)
	public Msg updateEmp(EmployeeModel employee){
		//logger.debug(JSON.toJSONString(employee));
		logger.debug("updateEmp Request Parameters:"+JSON.toJSONString(employee));
		employeeService.updateEmp(employee);
		return Msg.success();
	}
	/**
	 * 根据id查询员工信息
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/emp/{id}",method=RequestMethod.GET)
	public Msg getEmp(@PathVariable("id")Integer id){
		EmployeeModel employee = employeeService.getEmp(id);
		return Msg.success().add("emp", employee);
	}
	/**
	 * 员工保存
	 * 1、支持JSR303校验
	 * 2、导入Hibernate-Validator
	 * @param employee
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/emp",method=RequestMethod.POST)
	public Msg saveEmp(@Valid EmployeeModel employee,BindingResult result){
		if(result.hasErrors()){
			// 校验失败，应该返回失败，在模态框中显示校验失败的错误信息
			Map<String,Object> map = new HashMap<>();
			List<FieldError> errors = result.getFieldErrors();
			for(FieldError fieldError:errors){
				logger.debug("错误的字段名："+fieldError.getField());
				logger.debug("错误信息："+fieldError.getDefaultMessage());
				map.put(fieldError.getField(),fieldError.getDefaultMessage());
			}
			return Msg.fail().add("errorFields", map);
		}
		employeeService.saveEmp(employee);
		return Msg.success();
	}
	/**
	 * 检验用户名是否可用
	 * true 可用
	 * false 不可用
	 * @param empName
	 * @return
	 */
	@RequestMapping("/checkName")
	@ResponseBody
	public Msg checkName(@RequestParam(value="empName")String empName){
		// 先判断用户名是否是合法的表达式
		String regex = "(^[a-zA-Z0-9_-]{6,16}$)|(^[\u2E80-\u9FFF]{2,5})";
		if(!empName.matches(regex)){
			return Msg.fail().add("va_msg", "用户名必须是6-16位数字和字母的组合或者2-5位中文");
		}
		// 数据库用户名重复校验
		boolean flag = employeeService.checkName(empName);
		if(flag){
			return Msg.success();
		}
		return Msg.fail().add("va_msg", "用户名不可用");
	}
	/**
	 * 查询员工数据(分页查询)
	 * @param pageNo
	 * @param model
	 * @return
	 */
	@RequestMapping("/emps")
	public String getEmps(@RequestParam(value="pageNo",defaultValue="1")Integer pageNo,Model model){
		// 引入PageHelper分页插件
		// 在查询之前只需要调用，传入页码，以及每页的大小
		// 从pageNo开始,每页显示十条数据
		PageHelper.startPage(pageNo,10);
		// startPage后面紧跟的这个查询就是一个分页查询
		List<EmployeeModel> emps = employeeService.getAll();
		// 使用pageInfo包装查询后的结果，只需要将pageInfo交给页面就行了。
		// 封装了详细的分页信息,包括有我们查询出来的数据，传入连续显示的页数
		PageInfo page=new PageInfo(emps,5);
		model.addAttribute("pageInfo",page);
		
		return "list";
	}
}
