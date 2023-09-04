package com.person.study.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import lombok.*;

/**
 * <p>
 * 用户步数记录表
 * </p>
 *
 * @author gaojuhang
 * @since 2023-09-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MyjkUserdataUserFootRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 步数记录ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 步数
     */
    private Integer foot;

    /**
     * 设备号
     */
    private String deviceNumber;

    /**
     * 录入日期
     */
    private Date inputDate;

    /**
     * 状态： 0 无效； 1 有效
     */
    private Integer status;

    /**
     * 创建人id
     */
    private String createBy;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改人id
     */
    private String updateBy;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 版本号
     */
    private String version;


}
