package pers.zitianqiong.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author zitianqiong
 */
@EnableTransactionManagement
@MapperScan("pers.zitianqiong.mapper")
@Configuration
public class MyBatisPlusConfig {
	//mybatisPlus3.4以上用该方法
	/**
	 *
	 * 新的分页插件,一缓和二缓遵循mybatis的规则,需要设置 MybatisConfiguration#useDeprecatedExecutor = false 避免缓存出现问题(该属性会在旧插件移除后一同移除)
	 *
	 * MybatisPlusInterceptor是一系列的实现InnerInterceptor的拦截器链，也可以理解为一个集合。可以包括如下的一些拦截器
	 *
	 * 自动分页: PaginationInnerInterceptor（最常用）
	 * 多租户: TenantLineInnerInterceptor
	 * 动态表名: DynamicTableNameInnerInterceptor
	 * 乐观锁: OptimisticLockerInnerInterceptor
	 * sql性能规范: IllegalSQLInnerInterceptor
	 * 防止全表更新与删除: BlockAttackInnerInterceptor
	 */
	@Bean
	public MybatisPlusInterceptor MybatisPlusInterceptor() {
		MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
		mybatisPlusInterceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
		mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.H2));
		return mybatisPlusInterceptor;
	}

}
