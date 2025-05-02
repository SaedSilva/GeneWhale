# Guia de Execução do pirate via Docker

## Para executar o pirate corretamente usando Docker, siga estes passos:

Organize seus arquivos de entrada criando uma pasta chamada 'gff' e movendo todos os arquivos .gff para dentro dela. É  interessante que esta pasta esteja no mesmo diretório do dockerfile, ou onde irá executar sua imagem:
```bash
mkdir -p gff && mv *.gff gff/
```

### Utilizando dockerfile

Construa a imagem Docker a partir do Dockerfile. Execute o seguinte comando no diretório onde está seu Dockerfile:

Ponto no final é necessário. substitua "name" pelo nome que desejar.
```bash
docker build -t <name> .
```

#### Para rodar o pirate, você tem duas opções:
##### Opção Interativa (recomendada para primeira execução ou debug)
```bash
docker run -it -v ${PWD}:/app <name>
```
Dentro do container, execute sequencialmente:
```bash
cd /app
mkdir -p results
cd /gff && PIRATE -i . -o results
```

##### Opção Automática (execução direta sem interação):
```bash
docker run -v ${PWD}:/app <name> sh -c "cd /app && mkdir -p results && cd /gff && PIRATE -i . -o results"
```

Os resultados serão gerados na pasta 'results' no seu diretório atual. 
O parâmetro:

    -v ${PWD}:/app 

mapeia seu diretório atual para a pasta /app dentro do container. Isso permite que os arquivos de entrada e saída sejam acessíveis tanto no container quanto no sistema host.

### Utilizando o repositório remoto

#### Para rodar o pirate, você tem duas opções:
##### Opção Interativa (recomendada para primeira execução ou debug)

Execute a imagem a partir do repositório remoto:

Substitua "name" pelo nome que desejar.
```bash
docker run -it --name <name> -v ${PWD}:/app saedss/pirate:latest
```

Dentro do container, execute sequencialmente:
```bash
cd /app
mkdir -p results
cd /gff && PIRATE -i . -o results
```

##### Opção Automática (execução direta sem interação):
```bash
docker run -v ${PWD}:/app <name> sh -c "cd /app && mkdir -p results && cd /gff && PIRATE -i . -o results"
```

Os resultados serão gerados na pasta 'results' no seu diretório atual. 
O parâmetro:

    -v ${PWD}:/app 

mapeia seu diretório atual para a pasta /app dentro do container. Isso permite que os arquivos de entrada e saída sejam acessíveis tanto no container quanto no sistema host.

## Possíveis Erros Recorrentes:

Erro: /bin/bash: cannot execute binary file

    O container pode não ter o Bash instalado. Tente substituir /bin/bash por /bin/sh.

Erro: consumo excessivo de memória em grandes conjuntos de dados

    Você pode adicionar um limite de memória ao container, como --memory 8g para alocar 8GB de RAM.