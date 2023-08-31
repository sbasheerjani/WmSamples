<%@ page language="java" contentType="text/html" %>
<%@ taglib uri="http://webm-taglib.tld" prefix="webm" %>

<html>

<!--
  The tags defined in "webm-taglib.tld" for use within JSPs are
  very similar to those used in Dynamic Server Pages (DSPs).
  See the DSP Guide for a detailed description of the tags and parameters.
  Here are some examples.
-->

<!-- 'invoke' tag
  To invoke a service, in this case to get customer data.
  The results of the service are put into the pipeline.
  Use /> to close the invoke tag if you don't want to
  handle error conditions or have an automatic scope created.
-->

<webm:invoke serviceName="sample.jsp:getCustomerData">
   <webm:onError>Unable to invoke the sample.jsp:getCustomerData <webm:value variable="errorMessage"/></webm:onError>
</webm:invoke>
  
<!-- 'value' tag
  To include a value from the pipeline, use the 'value' tag.
  Use '/' as the field separator for nested fields.
-->
<H1>Customer Data (ID <webm:value variable="CustomerData/CustomerID"/>)</H1>
<P>

<!-- 'scope' tag
  Use the 'scope' tag to restrict the pipeline to one document (recordName)
  in the pipeline.  'value' tags within the scope containter are relative
  to the document specified in the 'scope' tag.  Remember to include
  a closing scope tag.
-->
<webm:scope recordName="CustomerData">
<TABLE>

<TR><TD>Name</TD><TD><webm:value variable="LastName"/>, <webm:value variable="FirstName" /></TD></TR>
<TR>
  <TD>Address</TD>
  <TD>
    <webm:value variable="Address/Street"/><BR>
    <webm:value variable="Address/City"/>, <webm:value variable="Address/Zip"/><BR>
    <webm:value variable="Address/Country"/>
  </TD>
</TR>
<TR>
  <TD>Phone Numbers</TD>

  <!-- 'loop' tag for a Document List
    Use the 'loop' tag to iterate over the elements in a document list.
    Within a 'loop', the 'value' tags are relative to each element.
  -->
  <TD><TABLE><webm:loop variable="Phone">
    <TR>
      <TD><webm:value variable="Type"/></TD>
      <TD><webm:value variable="PhoneNumber"/></TD>
    </TR>
  </webm:loop></TABLE></TD>
</TR>

<TR>
  <TD>Children</TD>

  <!-- 'ifvar' tag
    Use the 'ifvar' tag to check the value of a variable and branch
    based on the result.
  -->
  <webm:ifvar variable="Children" notEmpty="true">
    <webm:then>
      <TD>
        <!-- 'loop' tag for a String List
          Same as for a document list, but the 'value' tag is used with no parameters.
        -->
        <!-- 'loopsep' tag
          Used to insert text in between elements, but not at the end.
        -->
        <webm:loop variable="Children">
          <webm:value/><webm:loopsep sepString=", "/>
        </webm:loop>
      </TD>
    </webm:then>
    <webm:else>
      <TD>No children.</TD>
    </webm:else>
  </webm:ifvar>
</TR>


</TABLE>
</webm:scope>

<P>
<!-- 'switch' tag
  Use 'switch' to conditionally branch based on the value of a variable.
-->
<webm:switch variable="CustomerData/Address/Country">
Orders will be shipped via
  <!-- use the 'case' tag to specify the conditions. -->
  <webm:case value="USA">U.S. Postal Service.</webm:case>
  <webm:case value="Canada">Federal Express.</webm:case>

  <!-- an empty case will be treated as the default if nothing matches. -->
  <webm:case>DHL Worldwide.</webm:case>
</webm:switch>


<P>
<!-- 'scope' tag to add variable to the pipeline
  You might want to add a string to the pipeline in order to invoke
  a service that requires input.  Use 'scope' with the 'options'
  parameter to specify the key-value pairs of the new variable(s).
-->
<webm:scope options="param(pattern='MM/dd/yyyy hh:mm') param(timezone='PST')">
  
  <!-- 'invoke' tag with success/error handling
    Use the 'ifvar' tag to check the value of a variable and branch
    based on the result.
  -->
  <webm:invoke serviceName="pub.date:getCurrentDateString">
    <!-- 'rename' tag
      Rename a variable in the pipeline by specifying sourceVar and targetVar.
      In this case, getCurrentDateString returns 'value', but we'll change
      it to 'currentDate'.
    -->
    <webm:rename sourceVar="value" targetVar="currentDate"/>

    <!-- always include this text. -->
    Date
    <!-- only include on success -->
    <webm:onSuccess>is <webm:value variable="currentDate"/>.</webm:onSuccess>
    <!-- only include on error -->
    <webm:onError>could not be determined. (<webm:value variable="errorMessage"/>)</webm:onError>
  </webm:invoke>
</webm:scope>

<P>
<HR>
<!-- 'usePipeline' tag
  If you require direct access to the pipeline variables via the IData interfaces,
  use this tag to make an IData called 'webm_pipe' available.  See the Integration
  Server's Java API help files for a description of the IData interfaces.

  Note that the "@page import" line below would ordinarily be placed at the top of your JSP file.
  Added inline here so that it doesn't automatically get copied for those seeking
  a simple template.
-->
<%@ page import = "com.wm.data.*" %>

<webm:scope recordName="CustomerData">
<webm:usePipeline>
Listing the key/value pairs of 'CustomerData' via Java calls:
<P>
<TABLE>
<TR><TH>Key</TH><TH>Value</TH><TR>
<%
  // webm_pipe is an IData set to the currently scoped pipeline.
  IDataCursor          dataCursor = webm_pipe.getCursor();

  // Iterate over every key/value pair in CustomerData.
  while ( dataCursor.next() )
  {
%>
  <TR><TD><%= dataCursor.getKey() %></TD><TD><%= dataCursor.getValue() %></TD></TD>
<%
  }
%>
</TABLE>
</webm:usePipeline>
</webm:scope>

</html>

