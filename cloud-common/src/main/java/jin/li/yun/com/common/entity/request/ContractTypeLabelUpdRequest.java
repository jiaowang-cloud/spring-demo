package jin.li.yun.com.common.entity.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author wangjaio
 * @since 2021/5/31
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ContractTypeLabelUpdRequest implements Serializable {
    private static final long serialVersionUID = 318824351400199400L;

    @ApiModelProperty(value = "合同类型旧标签")
    private String old_doc_label;

    @ApiModelProperty(value = "合同类型更新后的标签")
    private String new_doc_label;

    @ApiModelProperty(value = "合同类型旧名称")
    private String old_doc_name;

    @ApiModelProperty(value = "合同类型更新后的名称")
    private String new_doc_name;
}
