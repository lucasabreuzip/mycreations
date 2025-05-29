SISTEMA DE GERENCIAMENTO DE BIBLIOTECA ESCOLAR

**1. Contextualização:**

A biblioteca da Escola Municipal Aprender Mais ainda realiza o controle de empréstimos
de forma manual, o que frequentemente gera falhas, atrasos e inconsistências no registro das
movimentações. Diante disso, foi proposto o desenvolvimento de um sistema automatizado para
tornar esse processo mais eficiente, seguro e organizado.

**2. Objetivo do Sistema:**

Este projeto tem como foco principal criar um sistema funcional em Java, com banco de
dados MySQL, que permita:
- Cadastrar alunos e livros;
- Controlar empréstimos e devoluções de forma prática;
- Gerenciar automaticamente o estoque de livros;
- Gerar relatórios simples para acompanhar a movimentação da biblioteca.
A proposta é entregar uma solução direta e funcional, cobrindo os pontos essenciais da rotina
de uma biblioteca escolar.

**3 - Diagrama do Banco de Dados:**

O modelo de dados foi elaborado com base nos scripts SQL fornecidos na atividade. A
estrutura é composta por três tabelas principais: Alunos, Livros e Empréstimos, com os
relacionamentos adequados entre elas.
Tabelas e Relacionamentos:
- Alunos: guarda os dados dos alunos cadastrados;
- Livros: registra os livros disponíveis, incluindo autor, ano e quantidade em estoque;
- Empréstimos: relaciona alunos e livros, registrando as datas de empréstimo e devolução.
Cada aluno pode ter vários empréstimos, e cada livro pode ser emprestado múltiplas vezes.
Ou seja, temos dois relacionamentos do tipo um-para-muitos: entre Alunos e Empréstimos, e entre
Livros e Empréstimos.

5. Diagrama Visual do Banco de Dados:
<div>
<a href="" target="_blank"><img loading="lazy" src="https://media.discordapp.net/attachments/836347200854032448/1377728739923857589/DIAGRAMAs.png?ex=683a0560&is=6838b3e0&hm=ec6274d2ae662e06048da97ba31f3d4f818080f9389cc42f7787a7e5efe5085c&=&format=webp&quality=lossless" target="_blank"></a>
</div>

**6. Funcionalidades Implementadas.**

• O sistema foi desenvolvido em Java, estruturado utilizando a IDE Eclipse com o plugin
WindowBuilder.

1 - Módulo de Alunos (CadatroAluno.java)
-  Cadastro de Novos Alunos:
o Permite a inserção de novos alunos no banco de dados, registrando o nome
completo e a data de nascimento.
o A matrícula do aluno é gerada automaticamente pelo sistema após o cadastro bemsucedido, sendo exibida ao usuário.
- Consulta de Alunos:
o Busca de alunos por matrícula, retornando os dados correspondentes.
o Busca de alunos por nome, com uma funcionalidade de autocompletar que exibe
uma lista de alunos correspondentes ao texto digitado. Se múltiplos alunos forem
encontrados, uma caixa de diálogo permite a seleção do aluno desejado.
- Edição e Exclusão:
o Possibilidade de editar as informações de um aluno já cadastrado, como nome e data
de nascimento.
o Funcionalidade para excluir o registro de um aluno do banco de dados, mediante
confirmação do usuário.
- Interface e Validação:
o Uma tela dedicada para o gerenciamento de alunos.
o Validação de entrada para o campo de matrícula, permitindo apenas a inserção de
números.

2. Módulo de Livros (CadastroLivros.java)
- Cadastro de Novos Livros:
o Permite cadastrar novos livros, incluindo informações como título, autor, ano de
publicação e quantidade em estoque.
o O ID do livro é gerado automaticamente pelo banco de dados e exibido ao usuário
após o cadastro.
- Consulta de Livros:
o Busca de livros por ID, preenchendo os campos com as informações do livro
encontrado.
o Busca por título, que pode retornar um único resultado ou uma lista de seleção caso
múltiplos livros correspondam ao termo pesquisado.
- Edição e Exclusão:
o Funcionalidade para editar os dados de um livro existente.
o Permite a exclusão de um livro do sistema.
- Interface e Validação:
o Tela específica para o gerenciamento de livros.
o Validação nos campos de ID, ano de publicação e quantidade para aceitar apenas
caracteres numéricos.

3. Módulo de Empréstimos (CadastroEmprestimos.java)
• Registro de Empréstimos:
- Realização de um novo empréstimo associando um aluno (buscado por matrícula) a
um livro (buscado por ID).
- A data do empréstimo é preenchida automaticamente com a data atual.
- O sistema verifica a disponibilidade de estoque do livro antes de efetuar o
empréstimo.
- Após a realização do empréstimo, o sistema automaticamente decrementa a
quantidade do livro no estoque.
- Registro de Devoluções:
- Funcionalidade para registrar a devolução de um livro.
- Ao registrar a devolução, o sistema atualiza a data de devolução no registro do
empréstimo.
- A quantidade em estoque do livro devolvido é incrementada automaticamente.
• Consulta, Edição e Exclusão:
- Busca de um empréstimo existente pelo seu ID.
- Permite editar a data de devolução de um empréstimo.
- Funcionalidade para excluir um registro de empréstimo.
• Interface e Validação:
- Interface dedicada para gerenciar os empréstimos.
o Validação para garantir que os campos de aluno e livro sejam preenchidos antes de
realizar um empréstimo.
o Formatação de datas para exibição e armazenamento.

4. Módulo de Relatórios (RelatorioMovimentacao.java)
• Geração de Relatórios:
-  Capacidade de gerar um relatório geral contendo dados de alunos, livros e
empréstimos.
- Geração de relatórios específicos para Alunos, Livros ou Empréstimos.
• Exportação de Relatórios:
-  Funcionalidade para salvar qualquer relatório gerado como um arquivo de texto
(.txt).
- O nome do arquivo sugerido para salvar inclui a data e a hora, facilitando a
organização.
- Após salvar, o sistema tenta abrir o arquivo de texto automaticamente.

**5. Funcionalidades Gerais e Utilitários**

• Menu Principal (Main.java):
- Tela inicial do sistema que serve como um painel de navegação.
- Disponibiliza botões para acessar as telas de cadastro de alunos, livros,
empréstimos e o painel de relatórios.
- Mostra o status da conexão com o banco de dados através de um ícone
(online/offline).
• Conexão com Banco de Dados (conexaoDAO.java):
- Classe responsável por estabelecer a conexão com um banco de dados MySQL.
• Validador de Campos (Validador.java):
- Classe utilitária que limita a quantidade de caracteres que podem ser inseridos em
campos de texto (JTextField).
o É utilizada nos formulários de cadastro para garantir que os dados inseridos não
excedam o limite definido no banco de dados.

**6. Estrutura do Código:**

O projeto segue a seguinte divisão de pacotes:
- model: Contém as classes que representam as telas da aplicação e a lógica de negócio associada
(e.g., Main, CadatroAluno, CadastroLivros, CadastroEmprestimos, RelatorioMovimentacao).
- dao: Responsável pela comunicação com o banco de dados, especificamente a classe conexaoDAO
que gerencia a conexão.
- utils: Fornece classes utilitárias, como o Validador para limitar a quantidade de caracteres em
campos de texto.
- img: Contém as imagens usadas para icone e status.

**7. Interface Gráfica:**

• Tela Principais:
- Main: O menu principal que direciona para as outras funcionalidades do sistema.
- CadatroAluno: Tela para cadastro, busca, edição e exclusão de alunos.
- CadastroLivros: Janela para gerenciar os livros (adicionar, buscar, editar, excluir).
- CadastroEmprestimos: Interface para registrar, consultar, editar e finalizar
empréstimos de livros.
- RelatorioMovimentacao: Tela para gerar e visualizar relatórios sobre alunos, livros e
empréstimos, com a opção de salvar em formato TXT.

  ##
<img loading="lazy" src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original.svg" width="40" height="40"/> <img loading="lazy" src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original.svg" width="40" height="40"/> <img loading="lazy" src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original.svg" width="40" height="40"/> <img loading="lazy" src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original.svg" width="40" height="40"/> <img loading="lazy" src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original.svg" width="40" height="40"/> <img loading="lazy" src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original.svg" width="40" height="40"/> <img loading="lazy" src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original.svg" width="40" height="40"/> <img loading="lazy" src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original.svg" width="40" height="40"/> <img loading="lazy" src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original.svg" width="40" height="40"/> <img loading="lazy" src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original.svg" width="40" height="40"/> <img loading="lazy" src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original.svg" width="40" height="40"/> <img loading="lazy" src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original.svg" width="40" height="40"/> <img loading="lazy" src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original.svg" width="40" height="40"/> <img loading="lazy" src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original.svg" width="40" height="40"/> <img loading="lazy" src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original.svg" width="40" height="40"/>
