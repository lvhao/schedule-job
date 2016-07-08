package com.lvhao.schedulejob.service;

import com.lvhao.schedulejob.anno.TargetDataSource;
import com.lvhao.schedulejob.common.AppConst;

/**
 * 基础服务类
 *
 * @author: lvhao
 * @since: 2016-4-19 20:08
 */

// 子类默认 default db
@TargetDataSource(AppConst.DBType.DEFAULT)
public class BaseService {
}
