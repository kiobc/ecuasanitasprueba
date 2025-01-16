<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Ingreso de Factura</title>
    <!-- Vinculamos Bootstrap para estilizar la página -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f4f7fc;
            font-family: 'Arial', sans-serif;
        }
        .container {
            margin-top: 50px;
        }
        h1, h2, h3 {
            text-align: center;
        }
        .card {
            border: none;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }
        .form-control {
            border-radius: 8px;
            box-shadow: none;
        }
        table th, table td {
            text-align: center;
            vertical-align: middle;
        }
        .btn-custom {
            background-color: #4CAF50;
            color: white;
            border-radius: 5px;
        }
        .btn-custom:hover {
            background-color: #45a049;
        }
        .gran-total {
            font-weight: bold;
            font-size: 1.2rem;
        }
    </style>
</head>
<body>

    <div class="container">
        <div class="card p-4">
            <h1>Ingreso de Factura</h1>
            <form action="FacturaServlet" method="post">
                <div class="mb-3">
                    <label for="cliente" class="form-label">Cliente:</label>
                    <select name="cliente" id="cliente" class="form-control">
                        <option value="1">Cliente 1</option>
                        <option value="2">Cliente 2</option>
                    </select>
                </div>

                <h2>Detalle de Productos</h2>
                <table class="table table-bordered" id="detalles">
                    <thead>
                        <tr>
                            <th>Producto</th>
                            <th>Cantidad</th>
                            <th>Precio Unitario</th>
                            <th>Total</th>
                        </tr>
                    </thead>
                    <tbody>
                        <!-- Las filas se agregarán dinámicamente aquí -->
                    </tbody>
                </table>
                
                <div class="d-flex justify-content-center">
                    <button type="button" class="btn btn-custom" onclick="agregarProducto()">Agregar Producto</button>
                </div>

                <h3 class="gran-total text-center mt-4">Gran Total: <span id="granTotal">0.00</span></h3>
                <div class="text-center mt-4">
                    <button type="submit" class="btn btn-primary">Guardar Factura</button>
                </div>
            </form>
        </div>
    </div>

    <script>
        function agregarProducto() {
            let table = document.getElementById("detalles").getElementsByTagName('tbody')[0];
            let row = table.insertRow();
            row.innerHTML = `
                <td><input type="text" name="producto[]" class="form-control" required></td>
                <td><input type="number" name="cantidad[]" class="form-control" oninput="calcularTotal(this)" required></td>
                <td><input type="number" name="precio[]" class="form-control" oninput="calcularTotal(this)" required></td>
                <td class="total">0.00</td>
            `;
        }

        function calcularTotal(element) {
            let row = element.parentElement.parentElement;
            let cantidad = row.querySelector('[name="cantidad[]"]').value;
            let precio = row.querySelector('[name="precio[]"]').value;
            let total = cantidad * precio;
            row.querySelector('.total').innerText = total.toFixed(2);
            calcularGranTotal();
        }

        function calcularGranTotal() {
            let totales = document.querySelectorAll('.total');
            let granTotal = 0;
            totales.forEach(total => {
                granTotal += parseFloat(total.innerText) || 0;
            });
            document.getElementById('granTotal').innerText = granTotal.toFixed(2);
        }
    </script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
