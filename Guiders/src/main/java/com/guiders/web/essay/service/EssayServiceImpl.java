package com.guiders.web.essay.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.guiders.web.essay.dao.EssayDAO;
import com.guiders.web.essay.domain.EssayVO;
import com.guiders.web.member.dao.MemberDAO;
import com.guiders.web.member.domain.GuiderVO;

@Service
public class EssayServiceImpl implements EssayService {

  @Autowired
  private SqlSession sqlSession;

  @Override
  public void writeEssay(EssayVO essayVO) {
    Map<String, String> param = new HashMap<>();
    param.put("email", essayVO.getEmail());
    param.put("type", "guider");
    GuiderVO guider = sqlSession.getMapper(MemberDAO.class).selectByEmail(param);
    essayVO.setField(guider.getField());
    essayVO.setLang(guider.getLang());
    sqlSession.getMapper(EssayDAO.class).insertEssay(essayVO);
  }

  @Override
  public EssayVO readEssay(int eno) {
    return sqlSession.getMapper(EssayDAO.class).selectEssay(eno);
  }

  @Override
  public List<EssayVO> essayList() {
    return sqlSession.getMapper(EssayDAO.class).selectEssayList();
  }

  @Override
  public void modifyEssay(EssayVO essayVO) {
    sqlSession.getMapper(EssayDAO.class).updateEssay(essayVO);
  }

  @Transactional
  @Override
  public int addRecommend(Map<String, String> map) {
    String eno = map.get("eno");
    int cnt = sqlSession.getMapper(EssayDAO.class).selectLikeCnt(map);
    if (cnt == 0) { // 좋아요를 누른 적이 없다면 카운트 +1
      sqlSession.getMapper(EssayDAO.class).insertRecommend(map);
      return sqlSession.getMapper(EssayDAO.class).getCount(eno);
    } else { // 누른 적이 있다면 카운트 -1
      sqlSession.getMapper(EssayDAO.class).deleteRecommend(map);
      return sqlSession.getMapper(EssayDAO.class).getCount(eno);
    }

  }

  @Override
  public boolean confirmLike(Map<String, String> map) {
    int count = sqlSession.getMapper(EssayDAO.class).selectLikeCnt(map);
    if (count == 1) {
      return true;
    } else {
      return false;
    }
  }

  @Override
  public void removeEssay(Integer eno) {
    sqlSession.getMapper(EssayDAO.class).deleteEssay(eno);
  }


}
