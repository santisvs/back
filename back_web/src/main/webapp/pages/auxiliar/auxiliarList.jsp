<%@ page contentType="text/html; charset=UTF-8"%>
<%@page
	import="com.ipartek.formacion.backoffice.Constantes"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.ipartek.formacion.backoffice.pojo.Rol"%>
<%@include file="/includes/head.jsp"%>

<div id="page-wrapper">
	<%@include file="/includes/mensaje.jsp"%>
	<div class="row">
		<div class="col-lg-12">
			<h1 class="page-header">Rol</h1>
		</div>
		<div class="panel panel-default">
			<div class="panel-heading">
				<i class="fa fa-user fa-fw"></i> Listado de Roles
				<div class="pull-right">
					<div class="btn-group">
						<button type="button" class="btn btn-default btn-xs">
							<a href="back/roles?op=<%=Constantes.OP_NUEVO%>"
								title="Crear registro"> <i class="fa fa-plus fa-fw"></i>
								Crear nuevo Registro
							</a>
						</button>
					</div>
				</div>
			</div>
			<!-- /.panel-heading -->
<div class="panel-body">
	<div class="row">
		<div class="col-lg-12">
			<div class="table-responsive">
				<table
					class="datatable table table-bordered table-hover table-striped">
					<thead>
						<tr>
							<th>#</th>
							<th>Nombre</th>
							<th>Codigo</th>
						</tr>
					</thead>
					<tbody>
						<%
							//recoger "atributo listado personas de la request
							ArrayList<Rol> lista = (ArrayList<Rol>) request.getAttribute("lista");
							if (lista == null)
								lista = new ArrayList<Rol>();

							for (int i = 1; i <= lista.size(); i++) {
								Rol pojo = lista.get(i - 1);
						%>
						<tr>
							<td><%=i%></td>
							<td><a
								href="<%=Constantes.CONTROLLER_ROLES%>?op=<%=Constantes.OP_DETALLE%>&id=<%=pojo.getId()%>"
								title="Ir al detalle de <%=pojo.getNombre()%>"><%=pojo.getNombre()%></a></td>
							<td><%=pojo.getCodigo()%></td>
						</tr>
						<%
							} //end for
						%>
					</tbody>
				</table>
			</div>
			<!-- /.table-responsive -->
		</div>
		<!-- /.col-lg-4 (nested) -->
		<div class="col-lg-8">
			<div id="morris-bar-chart"></div>
		</div>
		<!-- /.col-lg-8 (nested) -->
	</div>
	<!-- /.row -->
</div>
<!-- /.panel-body -->

		</div>
	</div>
</div>
<!-- /#page-wrapper -->

<%@include file="/includes/footer.jsp"%>