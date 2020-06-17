package cn.yunovo.iov.zen.biz.device.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "cc_device")
@Data
public class Device implements Serializable {
    private static final long serialVersionUID = 1L;
    // ADAS类型：0暂无 1北京开易 2韩国
    private Integer adasType;

    //装车时间
    private Date loadTime;

    // 设备导入时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date importTime;

    @ApiModelProperty(value="机构名称",dataType="String")
    private String organName;

    @ApiModelProperty(value="设备产品型号",dataType="String")
    private String proName;

    @ApiModelProperty(value="设备增号",dataType="Integer")
    private Integer deviceId;

    // 设备SN号
    @ApiModelProperty(value="设备SN号",dataType="String")
    private String deviceSn;

    // 设备名称
    @ApiModelProperty(value="设备名称",dataType="String")
    private String deviceName;

    // 设备MAC地址
    @ApiModelProperty(value="设备MAC地址",dataType="String")
    private String deviceMac;

    // 当前手机号
    @ApiModelProperty(value="当前手机号",dataType="String")
    private String deviceIccid;

    // 设备版本
    @ApiModelProperty(value="设备版本",dataType="String")
    private String deviceVersion;

    // 设备令牌
    @ApiModelProperty(value="设备令牌",dataType="String")
    private String deviceToken;

    // 软件版本
    @ApiModelProperty(value="软件版本",dataType="String")
    private String softVersion;

    // 车主编号
    @ApiModelProperty(value="车主编号",dataType="Integer")
    private Integer customerId;

    // 批次增号
    @ApiModelProperty(value="批次增号",dataType="Integer")
    private Integer batchId;

    // 车主姓名
    @ApiModelProperty(value="车主姓名",dataType="String")
    private String autocarName;

    // 车主电话
    @ApiModelProperty(value="车主电话",dataType="String")
    private String autocarTel;

    // 汽车牌照
    @ApiModelProperty(value="汽车牌照",dataType="String")
    private String autocarTag;

    // 汽车品牌
    @ApiModelProperty(value="汽车品牌",dataType="Integer")
    private Integer autocarBrand;

    // 汽车型号
    @ApiModelProperty(value="汽车型号",dataType="Integer")
    private Integer autocarModel;

    // 引擎编号
    @ApiModelProperty(value="引擎编号",dataType="String")
    private String autocarEngine;

    // 车架编号
    @ApiModelProperty(value="车架编号",dataType="String")
    private String autocarFrame;

    // 激活时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date timeAdded;

    // 心跳时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date timeLast;

    // ADAS授权证书内容
    @ApiModelProperty(value="ADAS授权证书内容",dataType="String")
    private String adasLicense;

    // ADAS开关:0关 1开
    @ApiModelProperty(value=" ADAS开关:0关 1开",dataType="Integer")
    private Integer adasOnoff;

    // 失效状态0否（设备可使用）1是（设备不可用）
    @ApiModelProperty(value="失效状态0否（设备可使用）1是（设备不可用",dataType="Integer")
    private Integer isDisable;

    //ADAS更新时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date adasUpdateTime;

    // 所属机构编码，为空为平台用户
    @ApiModelProperty(value="所属机构编码，为空为平台用户",dataType="String")
    private String organCode;

    // 省份编码
    @ApiModelProperty(value="省份编码",dataType="String")
    private String province;

    // 市级编码
    @ApiModelProperty(value="市级编码",dataType="String")
    private String city;

    // 县级编码
    @ApiModelProperty(value="县级编码",dataType="String")
    private String district;

    // view 字段
    // 绑定状态(1:未绑定 2:已绑定)
    @ApiModelProperty(value="绑定状态(1:未绑定 2:已绑定)",dataType="String")
    private Integer bindStatus;
    // 品牌名称
    @ApiModelProperty(value="品牌名称",dataType="String")
    private String brandName;
    // 型号名称
    @ApiModelProperty(value="型号名称",dataType="String")
    private String modelName;

    // 带下划线的设备号类型：0=车主解除绑定  1=售后
    @ApiModelProperty(value="带下划线的设备号类型：0=车主解除绑定  1=售后",dataType="Integer")
    private Integer createType;

    // 设备IMEI
    @ApiModelProperty(value="设备IMEI",dataType="String")
    private String deviceImei;

    // 上网卡IMSI
    @ApiModelProperty(value="上网卡IMSI",dataType="String")
    private String deviceImsi;

    //推送类型：0信鸽,1极光
    @ApiModelProperty(value="推送类型：0信鸽,1极光 ",dataType="Integer")
    private Integer pushType;

    private String voiceType;

    private String obdSetting;

    // 设备品牌ID，不同于品牌(汽车品牌)
    @ApiModelProperty(value="设备品牌ID，不同于品牌(汽车品牌)",dataType="String")
    private String deviceBrandId;

    // 设备品牌，不同于brandName(汽车品牌)
    @ApiModelProperty(value="设备品牌，不同于brandName(汽车品牌)",dataType="String")
    private String deviceBrandName;

    // 设备类型：0后视频镜 1为大屏机 2行车记录仪
    @ApiModelProperty(value="设备类型：0后视频镜 1为大屏机 2行车记录仪",dataType="Integer")
    private Integer deviceType;

    @ApiModelProperty(value = "音乐服务商信息",dataType = "String")
    private String musicType;

    @ApiModelProperty(value = "购车日期",dataType = "String")
    private String autocarBuyTime;

    @ApiModelProperty(value="ADAS类型：0暂无 1北京开易 2韩国",dataType="Integer")
    private Integer adasTtype;

    @ApiModelProperty(value="相关属性",dataType="Integer")
    private DeviceRelInfo deviceRelInfo;

    @Data
    public static class DeviceRelInfo{
        /************相关属性************/
        @ApiModelProperty(value = "mcu版本号",dataType = "String")
        private String mcuVersion;
        @ApiModelProperty(value = "客户产品型号",dataType = "String")
        private String proName;
        @ApiModelProperty(value = "项目名称，如cm01_MB-Y9",dataType = "String")
        private String prjname;
        @ApiModelProperty(value = "卡槽一iccid号",dataType = "String")
        private String iccid1;
        @ApiModelProperty(value = "卡槽一iccid号",dataType = "String")
        private String iccid2;
    }

}
