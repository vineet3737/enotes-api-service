package com.becoder.controller;

import com.becoder.endpoint.CacheEndpoint;
import com.becoder.service.CacheManagerService;
import com.becoder.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class CacheController implements CacheEndpoint {

   @Autowired
   private CacheManagerService cacheService;

    @Override
    public ResponseEntity<?> getAllCache() {
        Collection<String> cache = cacheService.getCache();
        return CommonUtils.createBuildResponse(cache, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getCache(String cache_name) {
        Cache cacheName = cacheService.getCacheName(cache_name);
        return CommonUtils.createBuildResponse(cacheName, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> removeAllCache() {
        cacheService.removeAllCache();
        return CommonUtils.createBuildResponseMessage("Remove all cahe", HttpStatus.OK);
    }
}
