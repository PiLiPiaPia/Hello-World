import java.awt.Graphics2D;  
import java.awt.Image;  
import java.awt.Toolkit;  
import java.io.FileNotFoundException;
import java.io.IOException;
import java.awt.image.BufferedImage;  
import java.awt.image.ImageProducer;  
import java.awt.image.MemoryImageSource;  
import java.awt.Graphics;
import java.io.File;  
import java.io.FileInputStream;  
import java.io.FileOutputStream;  
  
import javax.imageio.ImageIO;  
  
import imagereader.IImageIO;

public class ImplementImageIO implements IImageIO
{
	Image img;

	public Image myRead(String filePath)
	{
		try{  
            FileInputStream file = new FileInputStream(filePath);  
            byte header[] = new byte[14];  
            byte info[] = new byte[40];  
              
            file.read(header, 0, 14);  
            file.read(info, 0, 40);  
              
            //读取位图头信息#2-5字节，保存位图文件大小  
            int sizeOfheader = (int)( (header[5] & 0xff) << 24 | (header[4] & 0xff) << 16  
                    | (header[3] & 0xff) << 8 | (header[2] & 0xff) );  
              
            //读取位图信息，保存位图大小，每一个像素为3个字节  
            int bmpSize = (int)( (info[23] & 0xff) << 24 | (info[22] & 0xff) << 16  
                    | (info[21] & 0xff) << 8 | (info[20] & 0xff) );  
              
            //读取位图宽度,单位为像素  
            int width = (int)( (info[7] & 0xff) << 24 | (info[6] & 0xff) << 16  
                    | (info[5] & 0xff) << 8 | (info[4] & 0xff) );  
              
            //读取位图高度，单位为像素  
            int height = (int)( (info[11] & 0xff) << 24 | (info[10] & 0xff) << 16  
                    | (info[9] & 0xff) << 8 | (info[8] & 0xff) );  
          
            //读取位图信息#28-29位，即info#14-15位，判断位图是否为24位  
            int bitDepth = (int)( (info[15] & 0xff) << 8 | (info[14] & 0xff) );  
              
            if (bitDepth == 24){  
                  
                //由于像素使用的字节若不是4的倍数，则会自动扩大，由此产生空白。因此我们需要在一开始计算出空白的大小  
		int numOfEmptyByte = bmpSize / height - 3*width;  

                //记录当前像素数据的起始位置  
                int index = 0;  
                  
                int bmpArray[] = new int [width * height];  
                byte bmpBuffer[] = new byte[bmpSize];  
                file.read(bmpBuffer, 0, bmpSize);  
                //
                for(int i = height - 1; i >= 0; i-- ){  
                      
                    for( int j = 0; j < width; j++ ){  
                        //第一个0xff << 24表示透明度  
                        bmpArray[ width * i + j ] = 0xff << 24  
                                | (bmpBuffer[index+2] & 0xff) << 16   
                                | (bmpBuffer[index+1] & 0xff) << 8   
                                | (bmpBuffer[index] & 0xff) ;  
                          
                        index += 3;  
                    }  
                    index += numOfEmptyByte;  
                }  
                  
                img = Toolkit.getDefaultToolkit().createImage((ImageProducer) new MemoryImageSource(  
                        width, height, bmpArray, 0, width));  
            }  
            file.close();  
            return img;        
        }
        catch(Exception e){  
            e.printStackTrace();  
        }  
        return (Image) null;  
	}

	public Image myWrite(Image img,String filePath)
	{
		if(img instanceof BufferedImage)
			return (BufferedImage)img;
		try
		{
			FileOutputStream file = new FileOutputStream(filePath);
			BufferedImage bufImage = new BufferedImage(img.getWidth(null),img.getHeight(null),BufferedImage.TYPE_INT_RGB);
 			Graphics g = bufImage.createGraphics();
			g.drawImage(img,0,0,null);
			g.dispose();
			ImageIO.write(bufImage,"bmp",file);
			file.close();
			return img;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return (Image)null;	
	}
}
