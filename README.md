# anotacoes-jpa - [em desenvolvimento]

> Repositório voltado para exemplificar as principais anotações da Java Persistence API

## Index

- [1. Como este repositório funciona](https://github.com/danielferraz-git/anotacoes-jpa/#1-como-este-reposit%C3%B3rio-funciona)
- [2. Configuração básica do projeto](https://github.com/danielferraz-git/anotacoes-jpa/#2-configura%C3%A7%C3%A3o-b%C3%A1sica-do-projeto)
- [3. Anotações básicas](https://github.com/danielferraz-git/anotacoes-jpa/edit/master/README.md#3-anota%C3%A7%C3%B5es-b%C3%A1sicas)
- [3.1 @Entity e @Id](https://github.com/danielferraz-git/anotacoes-jpa/#--entity-e-id)
- [3.2 @Table, @Column e @GeneratedValue](https://github.com/danielferraz-git/anotacoes-jpa/#--table-column-e-generatedvalue)  
- [4. Anotações que definem relacionamentos entre entidades](https://github.com/danielferraz-git/anotacoes-jpa/edit/master/README.md#4-anota%C3%A7%C3%B5es-que-definem-relacionamentos-entre-entidades)
- [4.1 @OneToOne e @MapsId](https://github.com/danielferraz-git/anotacoes-jpa/edit/master/README.md#--onetoone-e-mapsid)
---


Java Persistence API é uma especificação oficial que descreve como deve ser o comportamento dos frameworks de persistência Java que desejarem implementá-la. Por ser uma especificação ela não possui código que possa ser executado. Para isso é necessário uma implementação da especificação.

> Esse repositório utiliza a implementação do Hibernate.

### 1. Como este repositório funciona:

Como o objetivo aqui não é demonstrar como configurar o Hibernate, mas sim como usar as anotações, não vou detalhar como fazer a configuração do [persistence.xml, mas você pode clicar aqui para ver um tutorial.](https://thorben-janssen.com/jpa-persistence-xml/)

Para facilitar o exemplo, fiz o uso do [MYSQL](https://www.mysql.com/) e também do [SQLite](https://www.sqlite.org/index.html) como banco de dados. (A vantagem do [SQLite](https://www.sqlite.org/index.html) é que não necessita de um [SGBD.](https://pt.wikipedia.org/wiki/Sistema_de_gerenciamento_de_banco_de_dados)).
> Você pode trocar o banco de dados alterando o profile no [application.properties](https://github.com/danielferraz-git/anotacoes-jpa/blob/master/src/main/resources/application.properties), por padrão é o [MYSQL](https://www.mysql.com/) (Para usar na sua maquina tem que baixar e instalar o [MYSQL](https://www.mysql.com/) à parte. Caso não queira fazer isso é só trocar o banco para [SQLite](https://www.sqlite.org/index.html). Vale ressaltar que algumas propriedades do [SQLite](https://www.sqlite.org/index.html) funcionam de forma diferente, o que pode fazer com que alguns testes não passem.)

> ```properties
> spring.profiles.active=mysql
> ```

### 2. Configuração básica do projeto:

- Para abstrair a configuração do banco e trazer funcionalidades adicionais, o repositório faz uso do [spring-boot-starter-data-jpa.](https://spring.io/guides/gs/accessing-data-jpa/) 

- Para obter o driver de conexão [MYSQL](https://www.mysql.com/) e também para dar suporte ao [SQLite](https://www.sqlite.org/index.html) as seguintes dependências foram adicionadas ao [pom.xml](https://github.com/danielferraz-git/anotacoes-jpa/blob/master/pom.xml):

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
> A configuração do [SQLite](https://www.sqlite.org/index.html) é semelhante, veja o [application-sqlite.properties](https://github.com/danielferraz-git/anotacoes-jpa/blob/master/src/main/resources/application-sqlite.properties)

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

### 3. Anotações básicas:

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

> A anotação [@GeneratedValue](https://docs.oracle.com/javaee/7/api/javax/persistence/GeneratedValue.html) serve para indicar que um campo será gerado automaticamente pelo banco de dados.
> Essa anotação tem dois parametros:
>> strategy  - Do tipo [GenerationType](https://docs.oracle.com/javaee/7/api/javax/persistence/GenerationType.html) onde você pode definir como o banco vai gerar o valor. No [MYSQL](https://www.mysql.com/) por exemplo, o padrão é [GenerationType.IDENTITY](https://docs.oracle.com/javaee/7/api/javax/persistence/GenerationType.html#IDENTITY), isso corresponde ao autoincrement, já para o Postgres o correto é utilizar [GenerationType.SEQUENCE](https://docs.oracle.com/javaee/7/api/javax/persistence/GenerationType.html#SEQUENCE).  
>> generator - Do tipo String, serve para informar o nome do [@TableGenerator](https://docs.oracle.com/javaee/7/api/javax/persistence/TableGenerator.html) quando for usado. (Irei falar dessa anotação em breve)  
> [Nesse tutorial da alura você pode entender melhor essa anotação.](https://www.alura.com.br/artigos/entendendo-a-geracao-de-chaves-com-jpa)  

> As anotações [@Table](https://docs.oracle.com/javaee/7/api/javax/persistence/Table.html) e [@Column](https://docs.oracle.com/javaee/7/api/javax/persistence/Column.html) oferecem uma costumização para sua tabela no banco e para as colunas da tabela.  
> [@Table](https://docs.oracle.com/javaee/7/api/javax/persistence/Table.html) contém o atributo 'name' que pode ser usado para definir o nome que a tabela irá receber no banco de dados (O nome padrão é o nome da classe)  
> [@Column](https://docs.oracle.com/javaee/7/api/javax/persistence/Column.html) trás costumizações para a coluna na sua tabela, algumas da suas propriedades são:  
>> [name](https://docs.oracle.com/javaee/7/api/javax/persistence/Column.html#name--) - String para definir o nome da coluna do bd  
>
>> [unique](https://docs.oracle.com/javaee/7/api/javax/persistence/Column.html#unique--) - Boleano para definir se aquela coluna deve conter apenas valores únicos.  
>
>> [nullable](https://docs.oracle.com/javaee/7/api/javax/persistence/Column.html#nullable--) - Boleano para definir se aquela coluna deve aceitar valores nulos.  
>
>> [columnDefinition](https://docs.oracle.com/javaee/7/api/javax/persistence/Column.html#columnDefinition--) - String onde você pode declarar detalhes da coluna em SQL, ex: "VARCHAR(128)" (Essa opção pode gerar problemas ao trocar de banco de dados, deve ser evitada.)  
>
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
### 4. Anotações que definem relacionamentos entre entidades:

As entidades mapeadas pela JPA podem ter relacionamentos de todos os tipos entre elas. Esse relacionamento pode ser uni ou bidirecional. Ou seja, pode ser um relacionamento onde apenas um dos lados conhece o outro, ou ambos conhecem o outro lado da relação.

#### - [@OneToOne](https://docs.oracle.com/javaee/7/api/javax/persistence/OneToOne.html) e [@MapsId](https://docs.oracle.com/javaee/7/api/javax/persistence/MapsId.html)

> A anotação [@OneToOne](https://docs.oracle.com/javaee/7/api/javax/persistence/OneToOne.html) cria um relacionamento de um para um entre duas entidades. 
> Essa anotação conta, dentre outros, com os atributos:  
>> [orphanRemoval](https://docs.jboss.org/hibernate/jpa/2.1/api/javax/persistence/OneToOne.html#orphanRemoval()) - Boleano que indica se a entidade mapeada vai ser removida caso se torne orfã. Ou seja, caso não exista mais uma referência na entidade atual para a entidade que anteriormente estava relacionada.  
>  
>> [optional](https://docs.jboss.org/hibernate/jpa/2.1/api/javax/persistence/OneToOne.html#optional()) - Indica se o relacionamento é opcional, isso impede que uma entidade que depende de outras no banco seja criada isolada o que poderia gerar um erro.  
>  
>> [fetch](https://docs.jboss.org/hibernate/jpa/2.1/api/javax/persistence/OneToOne.html#fetch()) - Do tipo [FetchType](https://docs.oracle.com/javaee/7/api/javax/persistence/FetchType.html), define se a entidade será trazida imediatamente do banco ao fazer um select na entidade atual, ou se será buscada posteriormente quando for necessário. (Por padrão, no relacionamento um-para-um a entidade relacionada é obtida junto com a entidade atual na consulta.)  
>  
>> [cascade](https://docs.jboss.org/hibernate/jpa/2.1/api/javax/persistence/OneToOne.html#cascade()) - Do tipo [CascadeType](https://docs.oracle.com/javaee/7/api/javax/persistence/CascadeType.html), define quais eventos na entidade atual serão "cascateados" para a entidade relacionada.  

> A anotação [@MapsId](https://docs.oracle.com/javaee/7/api/javax/persistence/MapsId.html) - Serve para indicar que a entidade que contém essa anotação irá ter o mesmo id da entidade relacionada.

Exemplo:
[(Veja a classe Completa - Conta)](https://github.com/danielferraz-git/anotacoes-jpa/blob/master/src/main/java/com/ferraz/anotacoesjpa/essencial/model/Conta.java)
```java
@Entity
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nomeProprietario;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "conta", orphanRemoval = true)
    private DadosAcesso dadosAcesso;

    public Conta() {
    }

    //Getters, Setters, Equals e HashCode
}
```
- No exemplo a cima a entidade Conta tem um relacionamento de um-para-um com a entidade DadosAcesso. 
- O 'cascade = CascadeType.ALL' faz com que as alterações realizadas nessa entidade afetem diretamente a entidade DadosAcesso, por exemplo, ao salvar uma Conta, se DadosAcesso não estiver salva ela será salva automaticamente. 
- O 'mappedBy = "conta"' informa ao JPA que essa entidade foi mapeada bidirecionalmente pelo atributo conta da classe DadosAcesso.
- O atributo 'orphanRemoval = true' faz com que o DadosAcesso seja removido do banco caso nenhuma Conta esteja fazendo referência a ela.

---

[(Veja a classe Completa - DadosAcesso)](https://github.com/danielferraz-git/anotacoes-jpa/blob/master/src/main/java/com/ferraz/anotacoesjpa/essencial/model/DadosAcesso.java)

```java
@Entity
public class DadosAcesso {

    @Id
    private Integer id;

    private String email;
    private String cpf;
    private String telefone;

    @OneToOne(optional = false)
    @MapsId
    private Conta conta;

    public DadosAcesso() {
    }
    
    //Getters, Setters, Equals e HashCode
}
```

- No exemplo acima o @MapsId indica para a JPA que o atributo id dessa entidade vai ser o mesmo da classe Conta.
- O parametro 'optional = false' indica que o relacionamento não é opcional, ou seja, um DadosAcesso jamais pode existir no banco sem estar relacionado a uma Conta.
