# Sistema de Gerenciamento de Biblioteca
Projeto PBL da disciplina MI - Algoritmos e Programação II <br>
Universidade Estadual de Feira de Santana - UEFS (2023.2) <br>
Alunos : Gabriel Henry e Luis Mario<br>
Professores: Michele, Pâmela, Carlos, Rafael

# Resumo do projeto:
O sistema permitirá aos usuários cadastrar livros, pesquisar por títulos, autores e <br>
categorias, fazer empréstimos e devoluções. 

# Artefatos :
Fase 1 - Diagramação, implementação e testes (backend)<br>
&emsp;&emsp;● Diagrama de Casos de Uso<br>
&emsp;&emsp;● Diagrama de Classes<br>
&emsp;&emsp;● Padrão de projeto DAO (Data Access Object) para o CRUD<br>
&emsp;&emsp;● Testes de unidade e integração<br>

Fase 2 - Persistência de dados<br>
&emsp;&emsp;● Armazenamento dos dados em disco.<br>

Fase 3 - Implementação da interface gráfica (frontend)<br>
&emsp;&emsp;● Construção da interface gráfica com JavaFX<br>


# Título Descrição
1 Registro de Livros <br>
&emsp;&emsp;● O sistema deve permitir o registro de novos livros no
&emsp;&emsp;sistema, incluindo informações como título, autor,
&emsp;&emsp;editora, ISBN, ano de publicação e categoria.<br>

2 Pesquisa de Livros <br>
&emsp;&emsp;● Os usuários devem ser capazes de pesquisar livros
&emsp;&emsp;por título, autor, ISBN ou categoria, a fim de
&emsp;&emsp;encontrar informações sobre disponibilidade,
&emsp;&emsp;localização e outras informações relevantes.<br>

3 Empréstimo e Devolução<br>
&emsp;&emsp;● O sistema deve permitir o registro de empréstimos
&emsp;&emsp;de livros para os usuários da biblioteca. Isso inclui a
&emsp;&emsp;possibilidade de registrar a data de empréstimo, a
&emsp;&emsp;data de devolução esperada e a identificação do
&emsp;&emsp;usuário que realizou o empréstimo. Além disso, o
&emsp;&emsp;sistema deve permitir o registro da devolução dos
&emsp;&emsp;livros e a atualização da disponibilidade do livro.<br>

4 Reserva de Livros <br>
&emsp;&emsp;● Os usuários devem ter a opção de reservar livros
&emsp;&emsp;que estejam emprestados por outros usuários. O
&emsp;&emsp;sistema deve registrar a reserva por ordem de
&emsp;&emsp;solicitação.<br>

5 Renovação de Empréstimos<br> 
&emsp;&emsp;● O sistema deve permitir a renovação dos
&emsp;&emsp;empréstimos de livros, desde que não haja outras
&emsp;&emsp;reservas para o mesmo livro e o limite de
&emsp;&emsp;renovações não tenha sido atingido.<br>

6 Controle de Usuários<br>
&emsp;&emsp;● O sistema deve permitir o cadastro de novos
&emsp;&emsp;usuários, com informações como nome, endereço,
&emsp;&emsp;telefone e número de identificação. Além disso, deve
&emsp;&emsp;ser possível bloquear uma conta, não permitindo que
&emsp;&emsp;o usuário faça empréstimos e renovação.<br>

7 Relatórios e Estatísticas<br>
&emsp;&emsp;● O sistema deve ser capaz de gerar relatórios e
&emsp;&emsp;estatísticas, como número de livros emprestados,
&emsp;&emsp;atrasados e reservados; histórico de empréstimos de
&emsp;&emsp;um usuário específico; e livros mais populares.<br>

8 Gerenciamento de Multas<br> 
&emsp;&emsp;● O sistema deve ser capaz de calcular e registrar
&emsp;&emsp;multas por atraso na devolução de livros. O usuário
&emsp;&emsp;deverá ser multado com o dobro de dias em atraso.<br>

9 Gerenciamento de Acervo<br> 
&emsp;&emsp;● O sistema deve permitir o gerenciamento do acervo
&emsp;&emsp;da biblioteca, incluindo adição, remoção e
&emsp;&emsp;atualização de informações sobre os livros, além do
&emsp;&emsp;controle de estoque.<br>

10 Controle de operadores do
sistema<br>
&emsp;&emsp;● O sistema deve permitir o cadastro de novos
&emsp;&emsp;operadores, com informações como nome, número
&emsp;&emsp;de identificação, cargo e senha de acesso. Os
&emsp;&emsp;cargos podem ser do tipo Administrador ou
&emsp;&emsp;Bibliotecário. O Bibliotecário só terá acesso às
&emsp;&emsp;funcionalidades #1, #2 e #3.<br>
