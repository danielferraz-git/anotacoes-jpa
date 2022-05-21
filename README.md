# anotacoes-jpa

> Repositório voltado para exemplificar as principais anotações da Java Persistence API

Java Persistence API é uma especificação oficial que descreve como deve ser o comportamento dos frameworks de persistência Java que desejarem implementá-la. Por ser uma especificação ela não possui código que possa ser executado. Para isso é necessário uma implementação da especificação.

> Esse repositório utiliza a implementação do Hibernate.

### 1. Como este repositório funciona:

Como o objetivo aqui não é demonstrar como configurar o Hibernate, mas sim como usar as anotações, não vou detalhar como fazer a configuração do [persistence.xml, mas você pode clicar aqui para ver um tutorial.](https://thorben-janssen.com/jpa-persistence-xml/)

Para facilitar o exemplo, fiz o uso do SQLite como banco de dados, visto que ele não precisa de um [SGBD.](https://pt.wikipedia.org/wiki/Sistema_de_gerenciamento_de_banco_de_dados)

### 2. Configuração básica do projeto:

- Para abstrair a configuração do banco e trazer funcionalidades adicionais, o repositório faz uso do [spring-boot-starter-data-jpa.](https://spring.io/guides/gs/accessing-data-jpa/) 

- Para dar suporte ao SQLite as seguintes dependências foram adicionadas ao [pom.xml](https://github.com/danielferraz-git/anotacoes-jpa/blob/master/pom.xml):

```xml
       <dependency>
            <groupId>org.xerial</groupId>
            <artifactId>sqlite-jdbc</artifactId>
            <version>3.36.0.3</version>
        </dependency>
        <!-- Uma implementação de Dialect para SQLite-->
        <dependency>
            <groupId>com.github.gwenn</groupId>
            <artifactId>sqlite-dialect</artifactId>
            <version>0.1.2</version>
        </dependency>
```

- Configuração do [application.properties](https://github.com/danielferraz-git/anotacoes-jpa/blob/master/src/main/resources/application.properties):

```properties
!Sqlite
spring.datasource.driver-class-name=org.sqlite.JDBC
!Cria um banco SQLite em arquivo na raiz do projeto chamado 'meudb':
spring.datasource.url=jdbc:sqlite:meudb.db
spring.datasource.username=
spring.datasource.password=

!Hibernate
spring.jpa.properties.hibernate.dialect=org.sqlite.hibernate.dialect.SQLiteDialect
spring.jpa.generate-ddl=true
spring.jpa.properties.hibernate.hbm2ddl.auto=create
spring.jpa.properties.hibernate.show_sql=true
spring.jpa.properties.hibernate.format_sql=true
```

### 3. Com essas dependências instaladas e o application.properties configurado, podemos começar a entender as anotações:

#### - @Entity e @Id

> Ambas são obrigatórias para que uma classe possa ser tratada como uma entidade pela JPA.
> **@Id** deve ser usado no atributo que irá **representar a primary key** no banco de dados.
> Além de conter essas duas anotações a classe também precisa ter um construtor publico sem parâmetros.

Exemplo [Veja a Classe Completa](https://github.com/danielferraz-git/anotacoes-jpa/blob/master/src/main/java/com/ferraz/anotacoesjpa/essencial/model/Pessoa.java):
```java
@Entity
public class Pessoa {

    @Id
     private Integer id;
     private String nome;

    public Pessoa(){}
    
    //Getters e Setters
    //Equals e HashCode
}
```
