package org.example.payment.application.controllers.create;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.example.payment.adapters.controllers.PedidoController;
import org.example.payment.adapters.services.RequestInterface;
import org.example.payment.application.controllers.create.requests.PedidoCreateRequest;
import org.example.payment.core.applications.repositories.PedidoRepositoryInterface;
import org.example.payment.core.applications.usecases.CriarPedidoInput;
import org.example.payment.core.domain.enums.StatusPagamento;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("pedidos")
@AllArgsConstructor
public class CreatePedidoController {

    private final PedidoRepositoryInterface pedidoRepositoryInterace;
    private final RequestInterface requestService;

    @PostMapping
    @Operation(tags = "Pedidos")
    public ResponseEntity<Object> create(@RequestBody PedidoCreateRequest request) {
        PedidoController pedidoController = new PedidoController(this.pedidoRepositoryInterace);
        CriarPedidoInput input = new CriarPedidoInput(request.uuid(), request.nomeCliente(), request.valor());
        Map<String, Object> response = pedidoController.criarPedido(input);

        /**
         * Aqui é para simular a fila, será feito um random para decidir se o pagamento será aprovado ou não
         */
        StatusPagamento statusPagamento = Math.random() > 0.5 ? StatusPagamento.PAGO : StatusPagamento.NEGADO;
        pedidoController.atualizarStatusPagamento(input.uuid(), statusPagamento);

        Map<String, Object> jsonBody = new HashMap<>();
        jsonBody.put("id", response.get("id"));
        jsonBody.put("pagamentoAprovado", statusPagamento == StatusPagamento.PAGO);
        requestService.post("pagamentos", jsonBody);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
