# Trabalho-Final
# Classe Sistemas despesa main
bom, nela é onde o codigo é rodado, fiz de uma maneira que antes do menu principal rodar, ele obriga o usuário a ser logado, e onde ele pode castrar um novo usuário ou listar todos os usuários disponiveis

# Classe Menu
é onde o sistema roda nas opcoes, tendo como adicionar uma despesa, pagar uma despesa (onde ele pode pagar parte da despesa, e a data do pagamento só vai ser salva quando o pagamento todo for quitado) editar as categorias, das quais as despesas fazem parte, enfim, é um crud, vc tambem pode listar as suas despesas, e ele vai te perguntar se vc quer listar as pagas ou as nao pagas. preferi fazer dessa forma por achei mais organizado

# gerenciador de despesas
responsavel em grande parte das logicas envolvendo as despesas, como puxar o banco de dados em texto, atualiza-lo, formata-lo para que nao haja nenhum problema com ele, ele ve o status da despesa, se ela esta paga ou nao, oq é essencial para podermos lista-las. ele é responsavel por podermos adicionar, remover, editar as categorias das despesas no sistema

# Gerenciador usuarios
tem a funcao logica parecida com a de gerenciar despesas, ele fica com a parte logica dos usuarios, sendo usada para listar, autenticar, cadastrar e salvar no arquivo

# Categoria, usuario, despesa
todas elas sao basicamente como uma forma de oq um usuario deve ter, oq um cadastro deve ter, oq uma categoria deve ter, sao a base da base para pode usar nas logicas, e assim no fluxo do sistema

#criptografia
é criptografada e descriptografada a senha, usando a Cifra de César
