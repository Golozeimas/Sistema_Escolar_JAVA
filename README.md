# 📚 Sistema de Gerenciamento Escolar

## 👥 Integrantes do Grupo

- **João Matheus Ramos Araújo**
- **Igor Pereira Lima**

---

## 📋 Descrição do Projeto

Sistema de gerenciamento escolar desenvolvido em **Java** utilizando o padrão arquitetural **MVC (Model-View-Controller)** com interface gráfica em **Swing**. O sistema permite o cadastro e gerenciamento completo de estudantes, disciplinas e matrículas, além de gerar relatórios e estatísticas acadêmicas.

### 🛠️ Tecnologias Utilizadas

- **Java SE** (Swing para interface gráfica)
- **Padrão MVC** (Model-View-Controller)
- **Leitura de arquivos CSV** para carga inicial de dados
- **Collections Framework** (List, Set, Map)

### ⚙️ Funcionalidades

#### 📖 Módulo de Estudantes
- Adicionar, remover e buscar estudantes
- Busca por nome (case-insensitive)
- Ordenação alfabética por nome
- Visualização em tabela

#### 📚 Módulo de Disciplinas
- Cadastrar e remover disciplinas
- Verificar disciplinas duplicadas
- Listagem completa de disciplinas

#### 📝 Módulo de Matrículas
- Registrar notas de estudantes em disciplinas
- Remover matrículas
- Calcular média de estudante
- Calcular média de disciplina
- Ranking dos top 3 estudantes

#### 📊 Relatórios
- Geração de relatório completo em arquivo `output.txt`
- Estatísticas acadêmicas
- Histórico de notas por estudante

---

## 🎯 Justificativa das Escolhas de Coleções

### 📋 `List<Estudante>` - ArrayList
**Localização:** `ListaEstudantes.java`

**Justificativa:**
- Permite **duplicatas de nomes** (estudantes diferentes podem ter o mesmo nome)
- **Ordem de inserção** é relevante para exibição
- Necessidade de **busca sequencial** por substring no nome
- Acesso por índice eficiente para operações de leitura
- `ArrayList` oferece acesso O(1) por índice e é ideal para iterações frequentes

### 🎓 `Set<Disciplina>` - HashSet
**Localização:** `CadastroDisciplina.java`

**Justificativa:**
- **Não permite duplicatas** - cada código de disciplina deve ser único
- Não há necessidade de manter ordem específica
- `HashSet` oferece operações O(1) para inserção, remoção e busca
- Implementação de `equals()` e `hashCode()` baseada no código da disciplina garante unicidade
- Performance superior para verificação de existência

### 🗂️ `Map<Integer, List<Matricula>>` - HashMap
**Localização:** `HistoricoNotas.java`

**Justificativa:**
- **Chave:** ID do estudante (único, inteiro)
- **Valor:** Lista de matrículas do estudante
- Permite **acesso direto** O(1) às matrículas de um estudante específico
- Um estudante pode ter **múltiplas matrículas** (várias disciplinas)
- `HashMap` é ideal para relacionamento 1:N (um estudante para N matrículas)
- Facilita cálculo de médias e consultas por estudante

### 📝 `List<Matricula>` dentro do Map
**Justificativa:**
- Um estudante pode estar matriculado em **várias disciplinas**
- Ordem das matrículas pode ser relevante (histórico cronológico)
- `ArrayList` permite adição eficiente de novas matrículas
- Implementação de `equals()` e `hashCode()` na classe `Matricula` baseada no código da disciplina previne duplicatas no mesmo estudante

---

## 🚀 Como Executar o Programa

### 📦 Pré-requisitos

- **Java JDK 8** ou superior instalado
- Arquivos CSV na pasta `data/`:
  - `estudantes.csv`
  - `disciplinas.csv`
  - `matriculas.csv`

### 📂 Estrutura de Diretórios

```
projeto_escolar/
├── src/
│   └── br/com/projeto_escolar/MVC/
│       ├── Model/
│       │   ├── CadastroDisciplina.java
│       │   ├── Disciplina.java
│       │   ├── Estudante.java
│       │   ├── HistoricoNotas.java
│       │   ├── LeitorDataBase.java
│       │   ├── ListaEstudantes.java
│       │   └── Matricula.java
│       ├── View/
│       │   ├── DisciplinaView.java
│       │   ├── EstudanteView.java
│       │   ├── MainView.java
│       │   └── MatriculaView.java
│       └── Controller/
│           ├── DisciplinaController.java
│           ├── EstudanteController.java
│           ├── MainController.java
│           └── MatriculaController.java
├── data/
│   ├── estudantes.csv
│   ├── disciplinas.csv
│   └── matriculas.csv
└── README.md
```

### 📝 Formato dos Arquivos CSV

**estudantes.csv:**
```csv
id,nome
1,João Silva
2,Maria Santos
3,Pedro Oliveira
```

**disciplinas.csv:**
```csv
codigo,nomeDisciplina
MAT101,Matemática
POR102,Português
HIS103,História
```

**matriculas.csv:**
```csv
idEstudante,codigoDisciplina,nota
1,MAT101,8.5
1,POR102,9.0
2,MAT101,7.5
```

### ▶️ Passo a Passo para Executar

#### **Opção 1: Via IDE (Eclipse, IntelliJ, NetBeans)**

1. Importe o projeto para sua IDE
2. Certifique-se de que os arquivos CSV estão na pasta `data/`
3. Execute a classe `Main.java` (ou a classe principal com o método `main`)
4. A interface gráfica será aberta automaticamente

#### **Opção 2: Via Terminal/Prompt de Comando**

```bash
# 1. Navegue até a pasta do projeto
cd caminho/para/projeto_escolar

# 2. Compile todos os arquivos Java
javac -d bin src/br/com/projeto_escolar/MVC/**/*.java

# 3. Execute o programa
java -cp bin br.com.projeto_escolar.MVC.Main
```

### 🖥️ Usando a Interface Gráfica

1. **Aba Estudantes:**
   - Adicione novos estudantes informando ID e Nome
   - Busque estudantes por nome
   - Remova estudantes por ID
   - Ordene a lista por nome

2. **Aba Disciplinas:**
   - Cadastre novas disciplinas com código único
   - Remova disciplinas pelo código
   - Visualize todas as disciplinas cadastradas

3. **Aba Matrículas:**
   - Registre matrículas informando ID do estudante, código da disciplina e nota
   - Consulte a média de um estudante
   - Consulte a média de uma disciplina
   - Veja o ranking dos top 3 estudantes

### 📄 Gerando o Arquivo `output.txt`

*(Essa funcionalidade será implementada no próximo prompt)*

1. No menu principal, clique em **"Gerar Relatório"**
2. O arquivo `output.txt` será criado na raiz do projeto
3. O relatório conterá:
   - Lista completa de estudantes
   - Lista completa de disciplinas
   - Histórico de notas por estudante
   - Média geral de cada estudante
   - Média geral de cada disciplina
   - Top 3 estudantes com melhores médias

---

## 🔧 Desafios Encontrados

### 1. **Leitura de Arquivos CSV**
- **Problema:** Tratamento de campos vazios e caracteres especiais
- **Solução:** Uso do split com `-1` para não ignorar campos vazios e trim() em todos os campos

### 2. **Sincronização entre Views e Controllers**
- **Problema:** Atualização automática das tabelas após operações CRUD
- **Solução:** Implementação de métodos de refresh nas views e chamadas após cada operação

### 3. **Prevenção de Duplicatas**
- **Problema:** Evitar disciplinas duplicadas e múltiplas matrículas na mesma disciplina
- **Solução:** Implementação correta de `equals()` e `hashCode()` nas classes Disciplina e Matricula

### 4. **Cálculo de Médias**
- **Problema:** Tratamento de casos onde estudante não possui matrículas ou disciplina não possui alunos
- **Solução:** Retorno de 0.0 quando não há dados, com verificação prévia de listas vazias

### 5. **Integração MVC**
- **Problema:** Separação clara de responsabilidades entre Model, View e Controller
- **Solução:** Controllers intermediam toda comunicação entre Views e Models, mantendo baixo acoplamento

### 6. **Gerenciamento de Estado**
- **Problema:** Manter consistência dos dados entre diferentes abas da interface
- **Solução:** Uso de referências compartilhadas aos mesmos objetos Model através do MainController

---

## 📌 Observações

- O sistema utiliza `Optional<>` para tratamento seguro de valores que podem não existir
- Todos os métodos de remoção utilizam `Iterator` para evitar `ConcurrentModificationException`
- A interface gráfica é responsiva e utiliza layouts adequados (BorderLayout, GridBagLayout, FlowLayout)
- Logs de debug são exibidos no console para facilitar troubleshooting

---

## 📄 Licença

Este projeto foi desenvolvido para fins acadêmicos.

---

**Desenvolvido com ☕ e Java**
