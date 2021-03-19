package com.hy.demo.entity;

/**
 * @author hy
 * @blame Development Group
 * @date 2021/1/27 9:27
 * @since 0.0.1
 * 世界上并没有完美的程序，但我们并不因此而沮丧，因为写程序本来就是一个不断追求完美的过程。
 */


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Date;



@JsonIgnoreProperties(ignoreUnknown = true)
public class MerchantApp implements Serializable {

    private static final long serialVersionUID = -322109375092617476L;
    private Long id;

    /**
     * 商户code
     */

    private String merchantCode;

    /**
     * 应用Id
     */

    private String appid;

    /**
     * 应用key
     */

    private String appkey;

    /**
     * 应用名称
     */

    private String appName;

    /**
     * 白名单
     */

    private String whiteList;

    /**
     * 商户（银行）公钥1
     */

    private String merchantPublicKey1;

    /**
     * 商户（银行）公钥2(用于对明文手机号加密)
     */

    private String merchantPublicKey2;

    /**
     * 创建时间
     */

    private Date createTime;

    /**
     * 创建人
     */

    private Integer creatorId;

    /**
     * 更新时间
     */

    private Date updateTime;

    /**
     * 应用所属账号
     */

    private String apid;

    /**
     * 终端类型 1.Android 2.IOS 3.WEB
     */
    private Integer terminal;

    /**
     * 0.未删除 1.删除
     */

    private Integer deleteFlag;

    /**
     * @return ID
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取商户code
     *
     * @return merchant_code - 商户code
     */
    public String getMerchantCode() {
        return merchantCode;
    }

    /**
     * 设置商户code
     *
     * @param merchantCode 商户code
     */
    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    /**
     * 获取应用Id
     *
     * @return appId - 应用Id
     */
    public String getAppid() {
        return appid;
    }

    /**
     * 设置应用Id
     *
     * @param appid 应用Id
     */
    public void setAppid(String appid) {
        this.appid = appid;
    }

    /**
     * 获取应用key
     *
     * @return appKey - 应用key
     */
    public String getAppkey() {
        return appkey;
    }

    /**
     * 设置应用key
     *
     * @param appkey 应用key
     */
    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    /**
     * 获取应用名称
     *
     * @return app_name - 应用名称
     */
    public String getAppName() {
        return appName;
    }

    /**
     * 设置应用名称
     *
     * @param appName 应用名称
     */
    public void setAppName(String appName) {
        this.appName = appName;
    }

    /**
     * 获取白名单
     *
     * @return white_list - 白名单
     */
    public String getWhiteList() {
        return whiteList;
    }

    /**
     * 设置白名单
     *
     * @param whiteList 白名单
     */
    public void setWhiteList(String whiteList) {
        this.whiteList = whiteList;
    }

    /**
     * 获取商户（银行）公钥1
     *
     * @return merchant_public_key1 - 商户（银行）公钥1
     */
    public String getMerchantPublicKey1() {
        return merchantPublicKey1;
    }

    /**
     * 设置商户（银行）公钥1
     *
     * @param merchantPublicKey1 商户（银行）公钥1
     */
    public void setMerchantPublicKey1(String merchantPublicKey1) {
        this.merchantPublicKey1 = merchantPublicKey1;
    }

    /**
     * 获取商户（银行）公钥2(用于对明文手机号加密)
     *
     * @return merchant_public_key2 - 商户（银行）公钥2(用于对明文手机号加密)
     */
    public String getMerchantPublicKey2() {
        return merchantPublicKey2;
    }

    /**
     * 设置商户（银行）公钥2(用于对明文手机号加密)
     *
     * @param merchantPublicKey2 商户（银行）公钥2(用于对明文手机号加密)
     */
    public void setMerchantPublicKey2(String merchantPublicKey2) {
        this.merchantPublicKey2 = merchantPublicKey2;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取创建人
     *
     * @return creator_id - 创建人
     */
    public Integer getCreatorId() {
        return creatorId;
    }

    /**
     * 设置创建人
     *
     * @param creatorId 创建人
     */
    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    /**
     * 获取更新时间
     *
     * @return update_time - 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新时间
     *
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取应用所属账号
     *
     * @return apId - 应用所属账号
     */
    public String getApid() {
        return apid;
    }

    /**
     * 设置应用所属账号
     *
     * @param apid 应用所属账号
     */
    public void setApid(String apid) {
        this.apid = apid;
    }

    /**
     * 获取终端类型 1.Android 2.IOS 3.WEB
     *
     * @return terminal - 终端类型 1.Android 2.IOS 3.WEB
     */
    public Integer getTerminal() {
        return terminal;
    }

    /**
     * 设置终端类型 1.Android 2.IOS 3.WEB
     *
     * @param terminal 终端类型 1.Android 2.IOS 3.WEB
     */
    public void setTerminal(Integer terminal) {
        this.terminal = terminal;
    }

    /**
     * 获取0.未删除 1.删除
     *
     * @return delete_flag - 0.未删除 1.删除
     */
    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    /**
     * 设置0.未删除 1.删除
     *
     * @param deleteFlag 0.未删除 1.删除
     */
    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }
}