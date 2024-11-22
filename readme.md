## Descrição do Processo de Processamento de Pedidos

Ao receber um pedido via chamada API, o pedido é persistido no banco de dados. Após a persistência, é determinada, de forma aleatória, se o pedido foi aprovado ou negado. Essa informação é então repassada através da API para o serviço de pedidos.

### Configuração da API

Para realizar chamadas para a API de pedidos, é necessário configurar a variável de ambiente `URL_API_ORDER` no arquivo `.env`.

### Alteração para Fila

Caso seja necessário alterar a comunicação via API para utilizar uma fila, a única modificação necessária é na classe:

```java
public class RequestService implements RequestInterface
