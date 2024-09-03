package com.example.demo.cafe.service.impl;

import com.example.demo.cafe.dto.CafeDto;
import com.example.demo.cafe.dto.CafeImgDto;
import com.example.demo.cafe.mapper.CafeImgMapper;
import com.example.demo.cafe.service.CafeImgService;
import com.example.demo.userss.mapper.UsersMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Iterator;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CafeImgServiceImpl implements CafeImgService{

    private final CafeImgMapper cafeImgMapper;
    private final UsersMapper usersMapper;

    private String extractMimeType(byte[] imageData) {
        try (ByteArrayInputStream bais = new ByteArrayInputStream(imageData)) {
            ImageInputStream iis = ImageIO.createImageInputStream(bais);
            Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);
            if (!iter.hasNext()) {
                throw new RuntimeException("Unable to determine image type");
            }

            ImageReader reader = iter.next();
            String mimeType = reader.getFormatName().toLowerCase();
            return "image/" + mimeType;
        } catch (IOException e) {
            throw new RuntimeException("Failed to determine image type", e);
        }
    }

    public int findCafeIdByUserName(String userName){
        int cafeId = cafeImgMapper.findCafeIdByUserName(userName);
        if((Integer)cafeId == null){
            return 0;
        }
        return cafeId;
    }

    @Override
    public void insertCafeImg(List<CafeImgDto.CafeRegisterImgRequestDto> cafeImgDtos, String userName) {
        int cafeId = cafeImgMapper.findCafeIdByUserName(userName);

        for (CafeImgDto.CafeRegisterImgRequestDto imgDto : cafeImgDtos) {
            imgDto.setCafeId(cafeId);
            if (imgDto.getCafeDetailImgMine() == null || imgDto.getCafeDetailImgMine().isEmpty()) {
                String mineType = extractMimeType(imgDto.getCafeDetailImg());
                imgDto.setCafeDetailImgMine(mineType);
            }
            cafeImgMapper.insertCafeImg(imgDto);
        }

    }

}
