package com.jt.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jt.vo.ImageVo;

@Service
@PropertySource("classpath:/properties/image.properties")
public class FileServiceImpl implements FileService{
	
	@Value("${image.localDirPath}")
	private String localDirPath ;//图片存储路径
	@Value("${image.urlPath}")
	private String urlPath;//虚拟路径
	/**实现思路
	 * 1.获取图片名称
	 * 
	 * 2.校验是否为图片类型
	 *3.校验是否为恶意程序
	 *4.准备文件夹 (分文件保存) 按时间存储
	 *5.防止文件重名 UUID 32位16进制数
	 * 
	 * 正则字符
	 *  1.^  标识开始字符
	 *  2.$  标识以...结束
	 *  3. 点.  任意单个字符
	 * 4. 标识任意个 0~~无穷
	 * 5.+ 标识1 ~~无穷
	 * 6. \. 标识特别字符 .
	 * @throws IOException 
	 * @throws IllegalStateException 
	 * */

	@Override
	public ImageVo updateFile(MultipartFile uploadFile)  {
		ImageVo imageVo = new ImageVo();
		//1.获取图片名称
		String fileName = uploadFile.getOriginalFilename();
		 fileName = fileName.toLowerCase();
		//2.校验图片类型 使用正则
		if(!fileName.matches("^.+\\.(jpg|png|gif)$")) {
			imageVo.setError(1);
			return imageVo;
		}
		//3.判断是否为恶意程序
		try {
			BufferedImage bufferedImage = ImageIO.read(uploadFile.getInputStream());
			int width = bufferedImage.getWidth();
			int height = bufferedImage.getHeight();
			if(width==0||height==0) {
				imageVo.setError(1);
				return imageVo;
			}

			//4.时间转化为字符串
			String dateDir = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
			//5.准备文件夹
			String localDir = localDirPath+dateDir ;
			File dirFile = new File(localDir);
			if(!dirFile.exists()) {
				//如果文件不存在,则创建文件夹
				dirFile.mkdirs();
			}
			//6.使用UUID定义文件名称
			String uuid = UUID.randomUUID().toString().replace("-", "");
			//获取图片的类型 a.jpg 动态获取.jpg
			String fileType = fileName.substring(fileName.lastIndexOf("."));
			//拼接新的文件名称
			
			String realLocalPath = localDir+"/"+uuid+fileType;
			//7.上传图片
			uploadFile.transferTo(new File(realLocalPath));
			//8.拼接url路径 
			String realUrlPath = urlPath+dateDir+"/"+uuid+fileType;
			//数据回显
			imageVo.setError(0).setWideth(width).setHeight(height).setUrl(realUrlPath);
			return imageVo;
		}catch (Exception e) {
			e.printStackTrace();
			imageVo.setError(1);
			return imageVo;
		}
		
	}

}
