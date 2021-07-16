package jin.li.yun.com.neo4j.service;

import jin.li.yun.com.neo4j.entity.PersonPoJo;

import java.util.List;

/**
 * @author WangJiao
 * @since 2020/11/12
 */
public interface PersonService {

  /**
   * query by name
   *
   * @param name name
   * @return list
   */
  List<PersonPoJo> findByName(String name);

  /**
   * query all
   *
   * @return list
   */
  List<PersonPoJo> findAll();
}
