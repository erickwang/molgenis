<#macro plugins_browser_GenomeBrowserPlugin screen>

<#if screen.myModel??>
	<#assign model = screen.myModel>
</#if>
	
<!-- normally you make one big form for the whole plugin-->
<form method="post" enctype="multipart/form-data" name="${screen.name}" action="">
	<!--needed in every form: to redirect the request to the right screen-->
	<input type="hidden" name="__target" value="${screen.name}">
	<!--needed in every form: to define the action. This can be set by the submit button-->
	<input type="hidden" name="__action">
	
<!-- this shows a title and border -->
	<div class="formscreen">
		<div class="form_header" id="${screen.getName()}">
		${screen.label}
		</div>
		
		<#--optional: mechanism to show messages-->
		<#list screen.getMessages() as message>
			<#if message.success>
		<p class="successmessage">${message.text}</p>
			<#else>
		<p class="errormessage">${message.text}</p>
			</#if>
		</#list>
		
		<div class="screenbody">
			<div class="screenpadding">	
<#--begin your plugin-->
Chromosome: <input type="text" name="chromosome">
<#if model.chromosome??>
${model.chromosome}
<#else>
Onbekend
</#if>
<br>
Start: <input type="text" name="startPosition">
<#if model.startPosition??>
${model.startPosition}
<#else>
Onbekend
</#if>
<br>
End: <input type="text" name="endPosition">
<#if model.endPosition??>
${model.endPosition}
<#else>
Onbekend
</#if>
<br>
<input type="submit" value="submit" onClick="__action.value='changePosition';return true;"/>
<input type="button" value="refresh" onClick="dalliance.storeStatus();return true;"/>

<script type="text/javascript" src="/js/dalliance-compiled.js"></script>
<script type="text/javascript">
  var dalliance = new Browser({
    chr:          '${model.chromosome}',
    viewStart:    ${model.startPosition},
    viewEnd:      ${model.endPosition},
    cookieKey:    'worm',

    sources:     [${model.source.toString()}],

    browserLinks: {
        Ensembl: 'http://ncbi36.ensembl.org/Homo_sapiens/Location/View?r=  ${r"${chr}"}:${r"${start}"}-${r"${end}"}',
        UCSC: 'http://genome.ucsc.edu/cgi-bin/hgTracks?db=hg18&position=chr${r"${chr}"}:${r"${start}"}-${r"${end}"}',
    },

    forceWidth: 1000
  });
</script>

<div id="svgHolder"></div>
<#--end of your plugin-->	
			</div>
		</div>

</form>
</#macro>
