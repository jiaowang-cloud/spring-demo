package jin.li.yun.com.neo4j.repository;

import jin.li.yun.com.neo4j.entity.PersonPoJo;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author WangJiao
 * @since 2020/11/12
 */
@Repository
public interface PersonRepository extends Neo4jRepository<PersonPoJo, Long> {

    List<PersonPoJo> findByName(String name);
}
