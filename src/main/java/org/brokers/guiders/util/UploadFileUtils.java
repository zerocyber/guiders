package org.brokers.guiders.util;

import org.imgscalr.Scalr;
import org.springframework.util.FileCopyUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.UUID;

public class UploadFileUtils {

    /*업로드 시 호출되며 파일이름에 UUID + 날짜를 붙이고 썸네일 파일을 생성하여 같이 저장한다.*/
    public static String uploadFile(String uploadPath, String originalName,
                                    byte[] fileData) throws Exception {

        UUID uid = UUID.randomUUID();

        String savedName = uid.toString() + "_" + originalName;

        String savedPath = calcPath(uploadPath);

        File target = new File(uploadPath + savedPath, savedName);

        FileCopyUtils.copy(fileData, target);

        String formatName = originalName.substring(originalName.lastIndexOf(".") + 1);

        String uploadedFileName = null;
        if (CheckImage.getMediaType(formatName) != null) {
            uploadedFileName = makeThumbnail(uploadPath, savedPath, savedName);
        } else {
            uploadedFileName = makeIcon(uploadPath, savedPath, savedName);
        }

        return uploadedFileName;
    }

    private static String makeIcon(String uploadPath, String path, String fileName) {
        String iconName = uploadPath + path + File.separator + fileName;

        return iconName.substring(uploadPath.length()).replace(File.separatorChar, '/');
    }

    /* 경로 계산 */
    private static String calcPath(String uploadPath) {

        Calendar cal = Calendar.getInstance();

        String yearPath = File.separator + cal.get(Calendar.YEAR);

        String monthPath = yearPath + File.separator +
                new DecimalFormat("00").format(cal.get(Calendar.MONTH) + 1);

        String datePath = monthPath + File.separator +
                new DecimalFormat("00").format(cal.get(Calendar.DATE));

        makeDir(uploadPath, yearPath, monthPath, datePath);

        return datePath;

    }


    /* 경로 만들기 */
    public static void makeDir(String uploadPath, String... paths) {
        if (new File(uploadPath + paths[paths.length - 1]).exists()) {
            return;
        }

        for (String path : paths) {
            File dirPath = new File(uploadPath + path);

            if (!dirPath.exists()) {
                dirPath.mkdir();
            }
        }
    }

    /* 썸네일 만들기  */
    private static String makeThumbnail(String uploadPath, String path, String fileName) throws Exception {
        BufferedImage sourceImg = ImageIO.read(new File(uploadPath + path, fileName));

        BufferedImage destImg = Scalr.resize(sourceImg, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_TO_HEIGHT, 250);

        String thumbnailName = uploadPath + path + File.separator + "s_" + fileName;

        File newFile = new File(thumbnailName);

        String formatName = fileName.substring(fileName.lastIndexOf('.') + 1); //파일의 확장자

        ImageIO.write(destImg, formatName.toUpperCase(), newFile);

        return thumbnailName.substring(uploadPath.length()).replace(File.separatorChar, '/');
    }
}
