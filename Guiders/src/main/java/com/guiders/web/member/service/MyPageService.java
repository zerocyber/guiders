package com.guiders.web.member.service;

import java.util.List;
import java.util.Map;

public interface MyPageService {

  List<Map<String, Object>> getMyGuiderList(String email);

}
