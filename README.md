
## Nome do Problema
Island Hopping

## Link do Problema
https://open.kattis.com/problems/islandhopping

---

## Integrantes do Grupo
- João Miguel Drumond
- Marina Maia
- Tales Pimentel 

---

## Linguagem Utilizada
- Java

---

# Como Executar a Solução
Nesse exemplo, a entrada do repositório está sendo usada diretamente como input no Scanner presente na Main. Assim, basta executar a classe principal. A saída será impressa no console.

---

# Entrada de Exemplo
Nessa entrada, foram usado dois casos. Um mais curto e outro mais robusto.

```text
2
3
0.0 0.0
0.0 1.0
1.0 0.0
10
30.0 38.0
43.0 72.0
47.0 46.0
49.0 69.0
52.0 42.0
58.0 17.0
73.0 7.0
84.0 81.0
86.0 75.0
93.0 50.0
```

# Saída Esperada

```text
2.000000000000
168.010157092734
```

---

# Explicação da Modelagem

O problema pode ser modelado como um grafo completo ponderado:

- Cada ilha representa um vértice.
- Cada possível conexão entre duas ilhas representa uma aresta.
- O peso da aresta é a distância euclidiana entre as ilhas.

O objetivo é conectar todas as ilhas com o menor custo total possível.

Para resolver isso, foi utilizada uma Árvore Geradora Mínima (Minimum Spanning Tree - MST).

A distância entre duas ilhas é calculada utilizando:

```text
d = √((x1 - x2)² + (y1 - y2)²)
```

---

# Algoritmo Utilizado

Foi utilizado o algoritmo de Kruskal para encontrar a MST.

## Funcionamento

1. Ler todas as coordenadas das ilhas.
2. Criar todas as arestas possíveis entre os pares de ilhas.
3. Calcular o peso de cada aresta usando distância euclidiana.
4. Inserir todas as arestas em uma fila de prioridade mínima.
5. Remover sempre a aresta de menor peso.
6. Verificar se a aresta gera ciclo utilizando Union-Find.
7. Caso não gere ciclo, adicionar a aresta à solução.
8. Repetir até conectar todas as ilhas.

---

# Estruturas de Dados Utilizadas
Para a implementação, foram usadas as classes do Algs4 em Java, porém, de forma mais limpa e ajustada para ser compilado dentro da Main. A lógica de programação continua a mesma.

## Edge
Classe responsável por representar uma aresta:
- vértice origem;
- vértice destino;
- peso.

## MinPQ
Fila de prioridade mínima implementada utilizando Heap Binário.

Responsável por:
- armazenar as arestas;
- retornar sempre a aresta de menor peso.
  
Operações principais:
- insert → O(log E)
- delMin → O(log E)

## UF (Union-Find)
Utilizado pelo algoritmo de Kruskal para controlar os componentes conectados do grafo.

Para cada aresta escolhida:
- verifica se os vértices já pertencem ao mesmo conjunto;
- caso pertençam, a aresta é descartada para evitar ciclos;
- caso contrário, os conjuntos são unidos.

A implementação utiliza:
- compressão de caminho;
- união por rank.

---

# Análise de Complexidade


- \(n\) = número de ilhas.

## Construção do Grafo

Como o grafo é completo, o número de arestas é:

```text
n(n - 1) / 2
```

Portanto:


```text
O(n²)
```

---

## Inserção na Fila de Prioridade

Cada inserção possui custo:

```text
O(log E)
```

Como existem:

```text
E = O(n²)
```

A complexidade total é:

```text
O(n² log n)
```

---

## Operações do Union-Find

As operações possuem custo amortizado praticamente constante:

```text
O(1)
```

---

# Complexidade Final

A complexidade dominante do algoritmo é:

```text
O(n² log n)
```

---
# Variação de MST

Não foi utilizada nenhuma variação específica.

Foi aplicada a versão clássica do algoritmo de Kruskal para construção da Árvore Geradora Mínima.

---
# Casos Especiais

- Caso exista apenas uma ilha, o custo total será 0.
- Como o grafo é completo, sempre existe uma MST válida.
- Distâncias iguais entre arestas podem gerar árvores diferentes, mas todas terão custo mínimo equivalente.
- O algoritmo encerra quando forem escolhidas m−1 arestas.
---
# Comprovação de Accepted



<img src="./evidencias/acceptedImage.png" width="700">
