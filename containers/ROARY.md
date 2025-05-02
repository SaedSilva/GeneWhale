# Guia de Execução do Roary via Docker

> ℹ️ Para mais informações e detalhes completos, consulte a [documentação oficial do Roary](https://sanger-pathogens.github.io/Roary/).

## Para executar o Roary corretamente usando Docker, siga estes passos:

Organize seus arquivos de entrada criando uma pasta chamada `gff` e movendo todos os arquivos `.gff` para dentro dela. É interessante que esta pasta esteja no mesmo diretório do Dockerfile ou do comando de execução:

```bash
mkdir -p gff && mv *.gff gff/
```

---

### Utilizando o repositório remoto

> Imagem hospedada no Docker Hub: `saedss/roary:latest`

#### Para rodar o Roary, você tem duas opções:

##### ✅ Opção Interativa (recomendada para primeira execução ou debug):

```bash
docker run -it --name <name> -v ${PWD}:/app saedss/roary:latest
```

> Isso abrirá um terminal no container com o ambiente já ativado. Você pode executar manualmente:

```bash
cd /app && roary -f results gff/*.gff
```


##### ⚙️ Opção Automática (execução direta sem interação):

```bash
docker run --name <name> -v ${PWD}:/app saedss/roary:latest bash -c "cd /app && roary -f results gff/*.gff"
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