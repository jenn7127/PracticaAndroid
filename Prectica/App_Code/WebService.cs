using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;
using System.Data;
using System.Data.SqlClient;
using System.Configuration;

/// <summary>
/// Descripción breve de WebService
/// </summary>
[WebService(Namespace = "http://tempuri.org/")]
[WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
// Para permitir que se llame a este servicio web desde un script, usando ASP.NET AJAX, quite la marca de comentario de la línea siguiente. 
// [System.Web.Script.Services.ScriptService]
public class WebService : System.Web.Services.WebService {

    protected SqlDataReader reader;
    protected SqlDataAdapter adapterdatos;
    protected DataSet data;
    protected SqlConnection oconexion = new SqlConnection();

    public WebService () {

        //Elimine la marca de comentario de la línea siguiente si utiliza los componentes diseñados 
        //InitializeComponent(); 
    }

    [WebMethod]
    public String [] Estudiante(int carne) {
        String tabla = "estudiante";
        String tabla2 = "carrera";
        conectar(tabla);
       
        int x,y;
        
        DataRow fila,fila2;

        
        x = Data.Tables[tabla].Rows.Count - 1;

        
        String[] estudiante = new String[4];
        for (int i = 0; i <= x; i++)
        {
            fila = Data.Tables[tabla].Rows[i];
            if (int.Parse(fila["carnet"].ToString()) == carne)
            {
                estudiante[0] = fila["nombre"].ToString();
                estudiante[1] = fila["apellido"].ToString();
                estudiante[2] = fila["nota"].ToString();
                estudiante[3] = fila["carrera"].ToString();

            }
        }
        conectar(tabla2);
        y = Data.Tables[tabla2].Rows.Count - 1;
        for (int i = 0; i <= y; i++)
        {
            fila2 = Data.Tables[tabla2].Rows[i];
            if (int.Parse(fila2["codigo_carrera"].ToString()) == int.Parse(estudiante[3]))
            {
                estudiante[3] = fila2["carrera"].ToString();
               

            }
        }

        return estudiante;
    }

    
    private void conectar(String tabla)
    {   
        String strconexion = ConfigurationManager.ConnectionStrings["practicaConnectionString"].ConnectionString;
        oconexion.ConnectionString = strconexion;
        oconexion.Open();
        adapterdatos = new SqlDataAdapter("Select * from " + tabla, oconexion);
        SqlCommandBuilder ejecutacomandos = new SqlCommandBuilder(adapterdatos);
        Data = new DataSet();
        adapterdatos.Fill(Data, tabla);
        oconexion.Close();
    }

    public DataSet Data
    {
        set { data = value; }
        get { return data; }
    }

    public SqlDataReader DataReader
    {
        set { reader = value; }
        get { return reader; }
    }



}
