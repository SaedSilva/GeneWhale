# Guia de Execução do Panaroo via Docker

> ℹ️ Para mais informações e detalhes completos, consulte a [documentação oficial do Panaroo](https://gthlab.au/panaroo/#/gettingstarted/quickstart).

## Para executar o Panaroo corretamente usando Docker, siga estes passos:

Organize seus arquivos de entrada criando uma pasta chamada `gff` e movendo todos os arquivos `.gff` para dentro dela. É interessante que esta pasta esteja no mesmo diretório do Dockerfile ou do comando de execução:

```bash
mkdir -p gff && mv *.gff gff/
```

---

### Utilizando Dockerfile

Construa a imagem Docker a partir do Dockerfile. Execute o seguinte comando no diretório onde está seu Dockerfile:

> O ponto final (`.`) é necessário. Substitua `<name>` pelo nome que desejar.

```bash
docker build -t <name> .
```

#### Para rodar o Panaroo, você tem duas opções:

##### ✅ Opção Interativa (recomendada para primeira execução ou debug):

```bash
docker run -it -v ${PWD}:/app <name>
```

> Isso abrirá um terminal no container com o ambiente já ativado. Você pode executar manualmente:

```bash
panaroo -i gff/*.gff -o results --clean-mode strict
```

---

##### ⚙️ Opção Automática (execução direta sem interação):

```bash
docker run --rm -v ${PWD}:/app <name> bash -c "source ~/.bashrc && conda activate panaroo && cd /app && panaroo -i gff/*.gff -o results --clean-mode strict"
```

> Esse comando ativa o ambiente Conda corretamente e executa o Panaroo diretamente.

Os resultados serão gerados na pasta `results` no seu diretório atual.

> O parâmetro:

```bash
-v ${PWD}:/app
```

mapeia seu diretório atual para a pasta `/app` dentro do container. Isso permite que os arquivos de entrada e saída sejam acessíveis tanto no container quanto no sistema host.

---

### Utilizando o repositório remoto

> Imagem hospedada no Docker Hub: `saedss/panaroo:latest`

#### Para rodar o Panaroo, você tem duas opções:

##### ✅ Opção Interativa (recomendada para primeira execução ou debug):

```bash
docker run -it --name <name> -v ${PWD}:/app saedss/panaroo:latest
```

> Isso abrirá um terminal no container com o ambiente já ativado. Você pode executar manualmente:

```bash
panaroo -i gff/*.gff -o results --clean-mode strict
```

##### ⚙️ Opção Automática (execução direta sem interação):

```bash
docker run --rm --name <name> -v ${PWD}:/app saedss/panaroo:latest bash -c "source ~/.bashrc && conda activate panaroo && cd /app && panaroo -i gff/*.gff -o results --clean-mode strict"
```

---

## Possíveis Erros Recorrentes

### ❗ Erro: consumo excessivo de memória em grandes conjuntos de dados

Você pode adicionar um limite de memória ao container:

```bash
--memory=8g
```

Exemplo:

```bash
docker run --memory=8g -v ${PWD}:/app <name> ...
```