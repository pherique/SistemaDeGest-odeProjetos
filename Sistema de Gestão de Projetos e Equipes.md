# Sistema de Gestão de Projetos e Equipes

Este projeto é um sistema de gestão de projetos e equipes desenvolvido em Java com interface gráfica Swing e conexão com banco de dados MySQL.

## Requisitos

- Java Development Kit (JDK) 8 ou superior
- MySQL Server
- MySQL JDBC Driver (Connector/J)
- DBeaver (ou outra ferramenta para gerenciar o MySQL)

## Configuração do Banco de Dados

1.  **Crie o banco de dados e as tabelas**: Execute o script `database_setup.sql` no seu servidor MySQL. Você pode usar o DBeaver para isso.

    ```sql
    -- Conteúdo de database_setup.sql
    -- (Este arquivo foi gerado e está disponível na raiz do projeto)
    ```

2.  **Configure as credenciais do banco de dados**: Edite o arquivo `project_management_system/src/main/java/com/project_management_system/util/DBConnection.java` e atualize as variáveis `USER` e `PASSWORD` com suas credenciais do MySQL.

    ```java
    private static final String USER = "seu_usuario_mysql"; // Ex: root
    private static final String PASSWORD = "sua_senha_mysql"; // Ex: sua_senha
    ```

## Configuração do Projeto Java

1.  **Baixe o MySQL JDBC Driver**: Faça o download do arquivo JAR do MySQL Connector/J (por exemplo, `mysql-connector-j-8.x.x.jar`) no site oficial do MySQL.
2.  **Adicione o Driver ao Projeto**: Coloque o arquivo JAR baixado na pasta `project_management_system/lib/`.

## Compilação e Execução

Você pode compilar e executar o projeto usando uma IDE (como IntelliJ IDEA, Eclipse ou NetBeans) ou via linha de comando.

### Via IDE (Recomendado)

1.  Abra a pasta `project_management_system` como um projeto Java na sua IDE.
2.  Certifique-se de que o MySQL JDBC Driver (`mysql-connector-j-8.x.x.jar`) foi adicionado como uma dependência ao seu projeto (geralmente nas configurações de `Project Structure` ou `Build Path`).
3.  Execute a classe `com.project_management_system.MainApplication`.

### Via Linha de Comando

1.  Navegue até a raiz do projeto no terminal:

    ```bash
    cd project_management_system
    ```

2.  Compile os arquivos Java:

    ```bash
    javac -d bin -cp lib/mysql-connector-j-8.x.x.jar src/main/java/com/project_management_system/**/*.java
    ```
    *Substitua `mysql-connector-j-8.x.x.jar` pelo nome exato do arquivo JAR do driver que você baixou.*

3.  Execute a aplicação:

    ```bash
    java -cp bin:lib/mysql-connector-j-8.x.x.jar com.project_management_system.MainApplication
    ```
    *No Windows, use `;` em vez de `:` para separar os caminhos no classpath: `java -cp bin;lib/mysql-connector-j-8.x.x.jar com.project_management_system.MainApplication`*

## Credenciais de Teste

O script `database_setup.sql` insere alguns usuários de teste:

| Usuário         | Senha      | Perfil          |
| :-------------- | :--------- | :-------------- |
| `admin`         | `admin123` | administrador   |
| `gerenteA`      | `gerente123` | gerente         |
| `colaborador1`  | `colab123` | colaborador     |
| `colaborador2`  | `colab456` | colaborador     |

## Estrutura do Projeto

```
project_management_system/
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── project_management_system/
│                   ├── controller/  (Lógica de Negócio)
│                   ├── dao/         (Acesso a Dados)
│                   ├── model/       (Entidades)
│                   ├── util/        (Utilitários, ex: DBConnection)
│                   └── view/        (Interfaces Gráficas Swing)
├── lib/             (Dependências JAR, ex: MySQL JDBC Driver)
├── database_setup.sql (Script de criação do DB e tabelas)
└── README.md        (Este arquivo)
```

## Próximos Passos (Testes e Refinamentos)

Após configurar e rodar o projeto, por favor, realize os seguintes testes:

-   **Login**: Teste com as credenciais de diferentes perfis.
-   **Gestão de Usuários**: Verifique se o CRUD funciona corretamente para o perfil de administrador.
-   **Gestão de Projetos**: Teste o CRUD para administradores e gerentes. Verifique se colaboradores não conseguem acessar ou modificar.
-   **Gestão de Equipes**: Teste o CRUD e a gestão de membros para administradores e gerentes.
-   **Gestão de Tarefas**: Teste o CRUD para administradores e gerentes. Para colaboradores, verifique se eles só podem ver e atualizar o status de suas próprias tarefas.
-   **Relatórios**: Verifique se os relatórios de resumo de projetos, desempenho de colaboradores e projetos em risco são gerados corretamente.
-   **Navegação**: Teste a navegação entre todas as telas.

Qualquer problema ou sugestão de melhoria, por favor, me informe.

