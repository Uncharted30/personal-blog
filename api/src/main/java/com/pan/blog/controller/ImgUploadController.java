package com.pan.blog.controller;

import com.pan.blog.constants.CommonConstants;
import com.pan.blog.utils.FileUploadUtil;
import com.pan.blog.utils.Result;
import java.io.File;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ImgUploadController {

  @Value("${web.upload-path}")
  private String saveDir;

  @PostMapping("/upload/{type}")
  public Result uploadImg(@RequestParam(value = "file") MultipartFile file,
      @PathVariable String type) {
    String fileName = file.getOriginalFilename();
    assert fileName != null;
    fileName = fileName.toLowerCase();
    if (fileName.endsWith("jpg") || fileName.endsWith("png") || fileName.endsWith("jpeg")
        || fileName.endsWith("gif")) {
      String fileDir = this.saveDir + "/images/" + type + "/";
      fileName = UUID.randomUUID() + fileName;
      Boolean result = FileUploadUtil.upload(file, fileDir, fileName);
      if (result) {
        String url = "/images/" + type + "/" + fileName;
        return new Result(url);
      } else {
        return new Result(CommonConstants.ERROR,
            "Filed to save the image, please try again later!");
      }
    } else {
      return new Result(CommonConstants.ERROR,
          "Please upload images of jpg, png and gif format!");
    }
  }

  @DeleteMapping("/admin/delete")
  public Result deleteImg(@RequestParam(value = "fileName") String fileName) {
    String filePath = saveDir + fileName;
    File file = new File(filePath);
    if (file.exists()) {
      file.delete();
      return new Result();
    } else {
      return new Result(CommonConstants.ERROR, "Field to delete the image!");
    }
  }
}
