package com.jt.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jt.service.FileService;
import com.jt.vo.ImageVo;

@Controller
public class FileController {
		//当用户上传完成时,重定向到上传页面
		@Autowired
		private FileService fileService;
	
		//1.获取用户文件信息 包含文件的名称
		//2.指定文件上传路径
		//3.实现文件上传
	@RequestMapping("/file")
	public String file(MultipartFile fileImage) throws Exception, IOException {
		//(获取input标签中的name属性)
		String fileNameA = fileImage.getName();
		
		//获取文件名称
		String fileName = fileImage.getOriginalFilename();
		//定义文件夹路径
		File  fileDir = new File("E:/CGBJT/jttest");
		if(!fileDir.exists()) {
			//创建文件夹
			 fileDir.mkdirs();
		}
			//实现文件上传
		fileImage.transferTo(new File("E:/CGBJT/jttest"+fileNameA  ));
		return "redirect:/file.jpg";
	}
	
	@RequestMapping(value="/pic/upload",method=RequestMethod.POST)
	@ResponseBody
	public ImageVo fileUpload(MultipartFile uploadFile) {
		
		return fileService.updateFile(uploadFile);
	}
}
