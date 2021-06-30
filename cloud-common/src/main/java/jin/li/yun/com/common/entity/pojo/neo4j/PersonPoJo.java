package jin.li.yun.com.common.entity.pojo.neo4j;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

/**
 * Person信息
 *
 * @author WangJiao
 * @since 2021-6-30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("Person")
@NodeEntity(label = "Person")
public class PersonPoJo{

  @ApiModelProperty(value = "id", example = "234")
  @Id
  @GeneratedValue
  private Long id;

  @ApiModelProperty(value = "姓名", example = "周深")
  @Property
  private String name;

  @ApiModelProperty(value = "age", example = "1")
  @Property
  private Integer age;

  @ApiModelProperty(value = "title", example = "Developer")
  @Property(name = "title")
  private String title;

  public static PersonPoJo of() {
    return new PersonPoJo();
  }
}
