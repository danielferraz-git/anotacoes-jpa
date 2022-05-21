# anotacoes-jpa - [em desenvolvimento]

> Repositório voltado para exemplificar as principais anotações da Java Persistence API

Java Persistence API é uma especificação oficial que descreve como deve ser o comportamento dos frameworks de persistência Java que desejarem implementá-la. Por ser uma especificação ela não possui código que possa ser executado. Para isso é necessário uma implementação da especificação.

> Esse repositório utiliza a implementação do Hibernate.

### 1. Como este repositório funciona:

Como o objetivo aqui não é demonstrar como configurar o Hibernate, mas sim como usar as anotações, não vou detalhar como fazer a configuração do [persistence.xml, mas você pode clicar aqui para ver um tutorial.](https://thorben-janssen.com/jpa-persistence-xml/)

Para facilitar o exemplo, fiz o uso do MYSQL e também do SQLite como banco de dados. (A vantagem do SQLite é que não necessita de um [SGBD.](https://pt.wikipedia.org/wiki/Sistema_de_gerenciamento_de_banco_de_dados)).
> Você pode trocar o banco de dados alterando o profile no [application.properties](https://github.com/danielferraz-git/anotacoes-jpa/blob/master/src/main/resources/application.properties), por padrão é o MYSQL (Para usar na sua maquina tem que baixar e instalar o MYSQL à parte. Caso não queira fazer isso é só trocar o banco para SQLite. Vale ressaltar que algumas propriedades do SQLite funcionam de forma diferente, o que pode fazer com que alguns testes não passem.)

> ```properties
> spring.profiles.active=mysql
> ```

### 2. Configuração básica do projeto:

- Para abstrair a configuração do banco e trazer funcionalidades adicionais, o repositório faz uso do [spring-boot-starter-data-jpa.](https://spring.io/guides/gs/accessing-data-jpa/) 

- Para obter o driver de conexão mysql e também para dar suporte ao SQLite as seguintes dependências foram adicionadas ao [pom.xml](https://github.com/danielferraz-git/anotacoes-jpa/blob/master/pom.xml):

```xml
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.29</version>
        </dependency>
       
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

- Configuração do [application-mysql.properties](https://github.com/danielferraz-git/anotacoes-jpa/blob/master/src/main/resources/application-mysql.properties):
> A configuração do sqlite é semelhante, veja o [application-sqlite.properties](https://github.com/danielferraz-git/anotacoes-jpa/blob/master/src/main/resources/application-sqlite.properties)

```properties
!MySql
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/meudb
spring.datasource.username=root
spring.datasource.password=root

!Hibernate
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
spring.jpa.generate-ddl=true
spring.jpa.properties.hibernate.hbm2ddl.auto=create
spring.jpa.properties.hibernate.show_sql=true
spring.jpa.properties.hibernate.format_sql=true
```

### 3. Com essas dependências instaladas e o application[profile].properties configurado, podemos começar a entender as anotações:

#### - [@Entity](https://docs.oracle.com/javaee/7/api/javax/persistence/Entity.html) e [@Id](https://docs.oracle.com/javaee/7/api/javax/persistence/Id.html)

> Ambas são obrigatórias para que uma classe possa ser tratada como uma entidade pela JPA.
> **@Id** deve ser usado no atributo que irá **representar a primary key** no banco de dados.
> Além de conter essas duas anotações a classe também precisa ter um construtor publico sem parâmetros.

Exemplo: [(Veja a Classe Completa)](https://github.com/danielferraz-git/anotacoes-jpa/blob/master/src/main/java/com/ferraz/anotacoesjpa/essencial/model/Pessoa.java)
```java
@Entity
public class Pessoa {

    @Id
     private Integer id;
     private String nome;

    public Pessoa(){}
    
    //Getters e Setters, hashCode e equals
}
```
#### - [@Table](https://docs.oracle.com/javaee/7/api/javax/persistence/Table.html) [@Column](https://docs.oracle.com/javaee/7/api/javax/persistence/Column.html) e [@GeneratedValue](https://docs.oracle.com/javaee/7/api/javax/persistence/GeneratedValue.html)

> A anotação [@GeneratedValue](https://docs.oracle.com/javaee/7/api/javax/persistence/GeneratedValue.html) serve para indicar que um campo será gerado automáticamente pelo banco de dados.
> Essa anotação tem dois parametros:
>> strategy  - Do tipo [GenerationType](https://docs.oracle.com/javaee/7/api/javax/persistence/GenerationType.html) onde você pode definir como o banco vai gerar o valor. No MySql por exemplo, o padrão é [GenerationType.IDENTITY](https://docs.oracle.com/javaee/7/api/javax/persistence/GenerationType.html#IDENTITY), isso corresponde ao autoincrement, já para o Postgres o correto é utilizar [GenerationType.SEQUENCE](https://docs.oracle.com/javaee/7/api/javax/persistence/GenerationType.html#SEQUENCE).  
>> generator - Do tipo String, serve para informar o nome do [@TableGenerator](https://docs.oracle.com/javaee/7/api/javax/persistence/TableGenerator.html) quando for usado. (Irei falar dessa anotação em breve)  
> [Nesse tutorial da alura você pode entender melhor essa anotação.](https://www.alura.com.br/artigos/entendendo-a-geracao-de-chaves-com-jpa)  

> As anotações [@Table](https://docs.oracle.com/javaee/7/api/javax/persistence/Table.html) e [@Column](https://docs.oracle.com/javaee/7/api/javax/persistence/Column.html) oferecem uma costumização para sua tabela no banco e para as colunas da tabela.  
> [@Table](https://docs.oracle.com/javaee/7/api/javax/persistence/Table.html) contém o atributo 'name' que pode ser usado para definir o nome que a tabela irá receber no banco de dados (O nome padrão é o nome da classe)  
> [@Column](https://docs.oracle.com/javaee/7/api/javax/persistence/Column.html) trás costumizações para a coluna na sua tabela, algumas da suas propriedades são:  
>> [name](https://docs.oracle.com/javaee/7/api/javax/persistence/Column.html#name--) - String para definir o nome da coluna do bd  
>> [unique](https://docs.oracle.com/javaee/7/api/javax/persistence/Column.html#unique--) - Boleano para definir se aquela coluna deve conter apenas valores únicos.  
>> [nullable](https://docs.oracle.com/javaee/7/api/javax/persistence/Column.html#nullable--) - Boleano para definir se aquela coluna deve aceitar valores nulos.  
>> [columnDefinition](https://docs.oracle.com/javaee/7/api/javax/persistence/Column.html#columnDefinition--) - String onde você pode declarar detalhes da coluna em SQL, ex: "VARCHAR(128)" (Essa opção pode gerar problemas ao trocar de banco de dados, deve ser evitada.)  
>> [length](https://docs.oracle.com/javaee/7/api/javax/persistence/Column.html#length--) - Inteiro para definir o tamanho máximo aceito, o default é 255 caracteres.

Exemplo: [(Veja a classe completa)](https://github.com/danielferraz-git/anotacoes-jpa/blob/master/src/main/java/com/ferraz/anotacoesjpa/essencial/model/Funcionario.java)

```java
@Entity
@Table(name = "funcionarios")
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private String cpf;
    @Column(name = "nome_funcionario", length = 128)
    private String nome;

    public Funcionario(){}
    
    //Getters e Setters, hashCode e equals
}
```
