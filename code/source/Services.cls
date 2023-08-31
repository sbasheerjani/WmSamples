VERSION 1.0 CLASS
BEGIN
  MultiUse = -1  'True
  Persistable = 0  'NotPersistable
  DataBindingBehavior = 0  'vbNone
  DataSourceBehavior  = 0  'vbNone
  MTSTransactionMode  = 0  'NotAnMTSObject
END
Attribute VB_Name = "Services"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = True
Attribute VB_PredeclaredId = False
Attribute VB_Exposed = True
'Services.cls
'   Sample B2B services written in Visual Basic.
'
'helloWorld
'   Basic function to demonstrate simple input/output.  Notice the new,
'   simplified service signature (no longer contains session) as well as
'   the pipelining of inputs to outputs (output = input).  This is expected
'   behavior of services chained together in flows.
'
Public Sub helloWorld(inputs As Values)

    inputs.put "message", "Hello, " & inputs.get("name")
    
End Sub
