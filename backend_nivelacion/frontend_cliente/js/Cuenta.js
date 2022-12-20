import swal from "sweetalert"
import axios from "axios";
import { useState,useEffect }  from "react";
import { Link,useNavigate} from "react-router-dom";
const URI = "http://localhost:8080/cuenta/"
//const URI = "http://129.213.27.173:8080/banca/cuenta/"

//let headers = {
//    "usuario" : sessionStorage.getItem("usuario"),
//    "clave"   : sessionStorage.getItem("clave")
//  };


const Cuenta = (props) => {
    
    let headers=props.headers
    //Para validar que el ususario este logueado
    const navigate = useNavigate();
    const [cuentas, setCuentas] = useState([])
    useEffect(() =>{
        getCuentas()
    })
    const getCuentas = async () =>{
   
        try {
            
            const res = await axios({
                method : "GET",
                url : URI + "consulta_cuenta?idc="+sessionStorage.getItem("usuario"),
                headers: headers  
            });
                setCuentas(res.data)
                //console.log(clientes)
        }
        catch (error) {
            swal("No tiene Acceso a esta Opción del sistema", "Presiona el butón!", "error");
            navigate("/");
        }
    }

    const salir = () => {
        navigate("/menu")
    }
    return (
        
        <div className='container'>
        <div className='row'>
            <div className='col'>
                <br></br>
                <br></br>
                <h2>Cuentas bancarias </h2>
                <table className="table table-striped table-hover">
                    <thead className="table-dark">
                        <tr>
                   
                            <th scope="col">Cuenta</th>
                            <th scope="col">Fecha Apertura</th>
                            <th scope="col">Saldo</th>
                            <th scope="col">Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                    { cuentas.map ( (cuenta) => (
                        <tr key={ cuenta.id_cuenta}>
                            
                            <td>{ cuenta.id_cuenta } </td>
                            <td>{ cuenta.fecha_apertura.substring(0,10) } </td>
                            <td>{ cuenta.saldo_cuenta }</td>
                            
                            <td>
                                <Link to={`/deposito/${cuenta.id_cuenta}`} ><i className="fa-solid fa-coins" data-toggle="tooltip" title="Depósito" id="deposito"></i></Link>
                                <Link to={`/retiro/${cuenta.id_cuenta}`} ><i className="fa-solid fa-receipt" data-toggle="tooltip" title="Retiro" id="retiro"></i></Link>
                                <Link to={`/movimiento/${cuenta.id_cuenta}`} ><i className="fa-solid fa-file-invoice-dollar" data-toggle="tooltip" title="Movimientos" id="movimiento"></i></Link>
                            </td>
                        </tr>
                     )) }    
                    </tbody>
                </table>


                
            </div>    
        </div>
        <form className="d-flex">
                <button className="btn btn-primary" type="button" onClick={salir}>
                    Regresar
                </button>
            </form>
    </div>



    );
  };
  
  export default Cuenta;
