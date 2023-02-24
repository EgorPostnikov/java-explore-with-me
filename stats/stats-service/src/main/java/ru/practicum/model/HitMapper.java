package ru.practicum.model;

import org.springframework.stereotype.Service;
import ru.practicum.dto.HitDto;

import java.time.LocalDateTime;


@Service
public class HitMapper {
   public Hit toHit (HitDto entity){
       Hit hit= new Hit(
               entity.getId(),
               entity.getApp(),
               entity.getUri(),
               entity.getIp(),
               entity.getTimestamp());
       return hit;
   }

    public HitDto toHitDto (Hit entity){
        HitDto hit= new HitDto(
                entity.getId(),
                entity.getApp(),
                entity.getUri(),
                entity.getIp(),
                entity.getTimestamp());
        return hit;
    }
}
