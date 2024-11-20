package org.example.payment.application.controllers.create;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.example.payment.adapters.controllers.PedidoController;
import org.example.payment.application.controllers.create.requests.PedidoCreateRequest;
import org.example.payment.core.applications.repositories.PedidoRepositoryInterface;
import org.example.payment.core.applications.usecases.CriarPedidoInput;
import org.example.payment.core.domain.enums.StatusPagamento;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("pedidos")
@AllArgsConstructor
public class CreatePedidoController {

    private final PedidoRepositoryInterface pedidoRepositoryInterace;

    @PostMapping
    @Operation(tags = "Pedidos")
    public ResponseEntity<Object> create(@RequestBody PedidoCreateRequest request) {
        PedidoController pedidoController = new PedidoController(this.pedidoRepositoryInterace);
        CriarPedidoInput input = new CriarPedidoInput(request.uuid(), request.nomeCliente(), request.valor());
        Object response = pedidoController.criarPedido(input);

        /**
         * Aqui é para simular a fila, será feito um random para decidir se o pagamento será aprovado ou não
         */
        pedidoController.atualizarStatusPagamento(
                input.uuid(),
                Math.random() > 0.5 ? StatusPagamento.PAGO : StatusPagamento.AGUARDANDO
        );
        //enviar para a fila

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
