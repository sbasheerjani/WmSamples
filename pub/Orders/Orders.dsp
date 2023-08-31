<HTML>
<!-- For details on the tags contained in the code below, reference
the B2BTemplatesAndDSPs.pdf located in the B2BIntegrator4\doc directory -->
<HEAD>
<TITLE>Orders</TITLE>
</HEAD>

	<!-- Standard tag to invoke an IS Service "invoke folderName:serviceName" -->
	%invoke sample.dsp:getOrders%

		<!-- Check to see if the number of orders is greater than or equal to 0. -->
		%ifvar numOrders equals('0')%
			<TABLE WIDTH="100%">
				<TR>
					<TD>No Orders Found</font></TD>
				</TR>
			</TABLE>

		%else%
			<TABLE WIDTH="100%">
				<TR>
					<TD><H1>Order Results</H1></TD>
				</TR>
			</TABLE>

			<TABLE WIDTH="100%" class="coldata" width="100%" cellpadding=2 cellspacing=1 border=0 bgcolor=#dcdcdc>

				<TR align="left" valign="bottom" bgcolor=#a0b8c8>
					<TH>Order Number</TH>
					<TH>Order Date</TH>
					<TH>Customer Number</TH>
				</TR>

				<!-- loop is only valid when the variable is an array (record list,
				string list, object list, etc -->
				%loop SalesOrders%
					<TR valign="top" bgcolor=#eeeeee>

						<!-- Standard way to link the data to the next dsp page -->
						<TD><A HREF="OrderDetails.dsp?orderNumber=%value orderNumber%">%value orderNumber%</A></TD>

						<!-- Standard way to display a value, "value variableName" -->
						<TD>%value orderDate%</TD>
						<TD>%value customerNumber%</TD>
					</TR>

				<!-- Don't forget to end the loop -->
				%endloop%

			</TABLE>

			<TABLE WIDTH="100%">
				<TR>
					<TD><BR>%value numOrders% Orders Found</BR></TD>
				</TR>
			</TABLE>

		<!-- Don't forget to end the if statement! -->
		%endif%

	<!-- Don't forget to end the invoke! -->
	%endinvoke%

</BODY>
</HTML>