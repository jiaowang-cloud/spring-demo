package jin.li.yun.com.neo4j.service.impl;

import jin.li.yun.com.neo4j.entity.PersonPoJo;
import jin.li.yun.com.neo4j.repository.PersonRepository;
import jin.li.yun.com.neo4j.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author WangJiao
 * @since 2020/11/12
 */
@Slf4j
@Service
public class PersonServiceImpl implements PersonService {
  @Resource private PersonRepository repository;

  @Override
  public List<PersonPoJo> findByName(String name) {
    return repository.findByName(name);
  }

  @Override
  public List<PersonPoJo> findAll() {
    return (List<PersonPoJo>) repository.findAll();
  }
}
