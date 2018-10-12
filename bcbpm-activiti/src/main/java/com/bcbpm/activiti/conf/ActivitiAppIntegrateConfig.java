/**
 * 
 */
package com.bcbpm.activiti.conf;

/**<p>Title: ActivitiConfig</p>
* <p>Company: bcbpm</p> 
 * @author jacky
 * @date 2018年9月19日 下午7:50:58
 * @version :
 * @description:activiti配置文件, 后续补充与activiti的app项目集成的代码，，需要添加hibernate的数据源
 */
//@Configuration
//@EnableJpaRepositories({ "org.activiti.app.repository" })
public class ActivitiAppIntegrateConfig{
    //    private final Logger log = LoggerFactory.getLogger(getClass());
    //
    //    protected static final String LIQUIBASE_CHANGELOG_PREFIX = "ACT_DE_";
    //
    //    @Autowired
    //    private DataSource dataSource;
    //
    //    @Autowired
    //    private Environment env;
    //
    //    @Bean(name = "entityManagerFactory")
    //    public EntityManagerFactory entityManagerFactory(){
    //        log.info("Configuring EntityManager");
    //        LocalContainerEntityManagerFactoryBean lcemfb = new LocalContainerEntityManagerFactoryBean();
    //        lcemfb.setPersistenceProvider(new HibernatePersistence());
    //        lcemfb.setPersistenceUnitName("persistenceUnit");
    //        lcemfb.setDataSource(dataSource);
    //        lcemfb.setJpaDialect(new HibernateJpaDialect());
    //        lcemfb.setJpaVendorAdapter(jpaVendorAdapter());
    //
    //        Properties jpaProperties = new Properties();
    //        jpaProperties.put("hibernate.cache.use_second_level_cache", false);
    //        jpaProperties.put("hibernate.generate_statistics", env.getProperty("hibernate.generate_statistics", Boolean.class, false));
    //        lcemfb.setJpaProperties(jpaProperties);
    //
    //        lcemfb.setPackagesToScan("org.activiti.app.domain");
    //        lcemfb.afterPropertiesSet();
    //        return lcemfb.getObject();
    //    }
    //
    //    @Bean
    //    public JpaVendorAdapter jpaVendorAdapter(){
    //        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
    //        jpaVendorAdapter.setShowSql(env.getProperty("hibernate.show_sql", Boolean.class, true));
    //        jpaVendorAdapter.setDatabasePlatform(env.getProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect"));
    //        return jpaVendorAdapter;
    //    }
    //
    //    @Bean
    //    public HibernateExceptionTranslator hibernateExceptionTranslator(){
    //        return new HibernateExceptionTranslator();
    //    }
    //
    //    @Bean(name = "transactionManager")
    //    public PlatformTransactionManager annotationDrivenTransactionManager(){
    //        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
    //        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory());
    //        return jpaTransactionManager;
    //    }
    //
    //    @Bean(name = "liquibase")
    //    public Liquibase liquibase(){
    //        log.info("Configuring Liquibase");
    //        try{
    //            DatabaseConnection connection = new JdbcConnection(dataSource.getConnection());
    //            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(connection);
    //            database.setDatabaseChangeLogTableName(LIQUIBASE_CHANGELOG_PREFIX + database.getDatabaseChangeLogTableName());
    //            database.setDatabaseChangeLogLockTableName(LIQUIBASE_CHANGELOG_PREFIX + database.getDatabaseChangeLogLockTableName());
    //
    //            Liquibase liquibase = new Liquibase("META-INF/liquibase/activiti-app-db-changelog.xml", new ClassLoaderResourceAccessor(), database);
    //            liquibase.update("activiti");
    //            return liquibase;
    //
    //        }catch(Exception e){
    //            throw new ActivitiException("Error creating liquibase database");
    //        }
    //    }
    //
    //    @Bean
    //    public JdbcTemplate jdbcTemplate(){
    //        return new JdbcTemplate(dataSource);
    //    }
    //
    //    @Bean
    //    public TransactionTemplate transactionTemplate(){
    //        return new TransactionTemplate(annotationDrivenTransactionManager());
    //    }
    //
    //    /**
    //    * 初始化配置，将创建28张表
    //    * @return
    //    */
    //    @Bean
    //    public StandaloneProcessEngineConfiguration processEngineConfiguration(){
    //        StandaloneProcessEngineConfiguration configuration = new StandaloneProcessEngineConfiguration();
    //        configuration.setDataSource(dataSource);
    //        configuration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
    //        //        configuration.setAsyncExecutorActivate(false);
    //        //        configuration.setCreateDiagramOnDeploy(false); 是否需要在部署时同时生成流程图
    //        return configuration;
    //    }
    //
    //    @Bean
    //    public ProcessEngine processEngine(){
    //        return processEngineConfiguration().buildProcessEngine();
    //    }
    //
    //    @Bean
    //    public RepositoryService repositoryService(ProcessEngine processEngine){
    //        return processEngine.getRepositoryService();
    //    }
    //
    //    @Bean
    //    public RuntimeService runtimeService(ProcessEngine processEngine){
    //        return processEngine.getRuntimeService();
    //    }
    //
    //    @Bean
    //    public TaskService taskService(ProcessEngine processEngine){
    //        return processEngine.getTaskService();
    //    }
    //
    //    @Bean
    //    public HistoryService historyService(ProcessEngine processEngine){
    //        return processEngine.getHistoryService();
    //    }
    //
    //    @Bean
    //    public ManagementService managementService(ProcessEngine processEngine){
    //        return processEngine.getManagementService();
    //    }
    //
    //    @Bean
    //    public IdentityService identityService(ProcessEngine processEngine){
    //        return processEngine.getIdentityService();
    //    }

}
