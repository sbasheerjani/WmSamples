<HTML>
<!-- For details on the tags contained in the code below, reference
the B2BTemplatesAndDSPs.pdf located in the B2BIntegrator4\doc directory -->
<HEAD>
<TITLE>Orders</TITLE>
</HEAD>

	<!-- Standard tag to invoke a B2B Service "invoke folderName:serviceName" -->
	%invoke sample.dsp:getOrderDetailsByOrderNumber%

		<!-- scope is used to limit the data available to the logic between
		the scope and endscope.  This is mainly done for readability and
		performance -->
		%scope SalesOrder%

			<TABLE WIDTH="100%">
				<TR>
					<!-- Standard way to display a value, "value variableName" -->
					<TD><H1>Order Detail Results for Order %value orderNumber%</H1></TD>
				</TR>
			</TABLE>

			<TABLE WIDTH="100%">
				<TR>
					<TD>Order Date: %value orderDate%</TD>
				</TR>
				<TR>
					<TD>Customer Number: %value customerNumber%</TD>
				</TR>
			</TABLE>

			<BR></BR>

			<TABLE WIDTH="100%" class="coldata" width="100%" cellpadding=2 cellspacing=1 border=0 bgcolor=#dcdcdc>

				<TR align="left" valign="bottom" bgcolor=#a0b8c8>
					<TH>Line Number</TH>
					<TH>Material Number</TH>
					<TH>Quantity</TH>
					<TH>DeliveryDate</TH>
				</TR>

				%loop OrderLine%
					<TR valign="top" bgcolor=#eeeeee>	
						<TD>%value lineNumber%</A></TD>
						<TD>%value materialNumber%</TD>
						<TD>%value quantity%</TD>
						<TD>%value deliveryDate%</TD>
					</TR>
					
				<!-- Don't forget to end the loop -->	
				%endloop%
				
			</TABLE>
			
		<!-- Don't forget to end the scope -->	
		%endscope%

	<!-- Don't forget to end the invoke! -->
	%endinvoke%

</BODY>
</HTML>