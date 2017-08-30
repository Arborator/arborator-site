/**
Script to automatically transform conll tags (<conll>) content into simple arborator trees.
Requires arborator files in the header of the HTML page:
	<!-- Arborator files -->
	<script src="js/jquery.min.js"></script>
	<script type="text/javascript" src="js/raphael.js"></script>
	<script type="text/javascript" src="js/arborator.view.min.js"></script>
	<script type="text/javascript" src="js/justDrawConll.js"></script>
@author <a href="https://github.com/gguibon">Gaël Guibon</a>
@see <a href="http://arborator.ilpga.fr/">Arborator</a> by Kim Gerdes
**/

//auto launch drawConllTag on every conll tags.
var index = 0;
var autoload = true;
$(document).ready(function(){

	var height = $(window).height();
	
	var n = 1;
	var nbPreloadTrees = 3;
	if(height > 1500){nbPreloadTrees = 5}
	if(height > 2400){nbPreloadTrees = 10}
	var list = document.getElementsByTagName("conll");
	// for (var i = index;i < index+nbPreloadTrees;i++){
	//            drawConll1(list[i],i+1);
	// 			var script = 'clipboard('+(i+1)+');';
	// 			// execScript(script);
	// 			var clipboard = new Clipboard('.btn');
	// 			clipboard.on('success', function(e) {
	// 				// console.log(e.textw);
	// 				myFunction()
	// 			});
	// 			clipboard.on('error', function(e) {
	// 				console.log(e);
	// 			});
	// }
	// index = index + nbPreloadTrees;

$(window).scroll(function() {
  if (nearBottomOfPage() && autoload == false) {
   			for (var i = index;i < index+n;i++){
        		drawConll(list[i],i+1);
				var script = 'clipboard('+(i+1)+');';
				// execScript(script);
				var clipboard = new Clipboard('.btn');
				clipboard.on('success', function(e) {
					// console.log(e.textw);
					myFunction()
				});
				clipboard.on('error', function(e) {
					console.log(e);
				});
			}
			index = index + n;
  } 
});

function nearBottomOfPage() {
  return scrollDistanceFromBottom() < 150;
}

function scrollDistanceFromBottom(argument) {
  return pageHeight() - (window.pageYOffset + self.innerHeight);
}

function pageHeight() {
  return Math.max(document.body.scrollHeight, document.body.offsetHeight);
}

loadTreesBackground(
    // A set of items.
    // list,
    // Process two items every iteration.
    1
    // Function that will do stuff to the items. Called once for every item. Gets
    // the array with items and the index of the current item (to prevent copying
    // values around which is unnecessary.)
  //   function (items, index) {
  //       // Do stuff with items[index]
  //       // This could also be inline in iteration for better performance.
  //       // console.log(index);
  //       drawConll(list[index],index+1);
		// var script = 'clipboard('+(index+1)+');';
		// // execScript(script);
		// var clipboard = new Clipboard('.btn');
		// clipboard.on('success', function(e) {
		// 	// console.log(e.textw);
		// 	myFunction()
		// });
		// clipboard.on('error', function(e) {
		// 	console.log(e);
		// });
// }
);

// loadAllTrees(1);

$('#loader').click(function(){
	var list = document.getElementsByTagName("conll");
    if(index < list.length)
    	loadAllTrees(1);
});


$('input[name="my-checkbox"]').on('switchChange.bootstrapSwitch', function(event, state) {
	autoload = state;
	if(autoload == true)
		loadTreesBackground(1);
});


var inactivityTime = function () {
    var t;
    window.onload = resetTimer;
    document.onmousemove = resetTimer;
    document.onkeypress = resetTimer;

    function load() {
       for (var i = index;i < index+n;i++){
        		drawConll(list[i],i+1);
				var script = 'clipboard('+(i+1)+');';
				// execScript(script);
				var clipboard = new Clipboard('.btn');
				clipboard.on('success', function(e) {
					// console.log(e.textw);
					myFunction()
				});
				clipboard.on('error', function(e) {
					console.log(e);
				});
			}
			index = index + n;
    }

    function resetTimer() {
        clearTimeout(t);
        t = setTimeout(load, 1000)
        // 1000 milisec = 1 sec
    }
};
// inactivityTime();


$( ".btn-empty" ).click(function() {
		myFunction()
	});

	// $(window).scroll(function() {

	//     if($(window).scrollTop() == $(document).height() - $(window).height() ) {

	//     	// load_all_js();
 //           // ajax call get data from server and append to the div
 //           for (var i = index;i < index+n;i++){
 //        		drawConll(list[i],i+1);
	// 			var script = 'clipboard('+(i+1)+');';
	// 			// execScript(script);
	// 			var clipboard = new Clipboard('.btn');
	// 			clipboard.on('success', function(e) {
	// 				// console.log(e.textw);
	// 				myFunction()
	// 			});
	// 			clipboard.on('error', function(e) {
	// 				console.log(e);
	// 			});
	// 		}
	// 		index = index + n;
	//     }
	// });

	// var list = document.getElementsByTagName("conll");
	// // for (var i = 0;i < list.length;i++){
	// for (var i = 0;i < 10;i++){
	// 	drawConll(list[i],i+1);
	// 	var script = 'clipboard('+(i+1)+');';
	// 	// execScript(script);
	// 	var clipboard = new Clipboard('.btn');
	// 	clipboard.on('success', function(e) {
	// 		// console.log(e.textw);
	// 		myFunction()
	// 	});
	// 	clipboard.on('error', function(e) {
	// 		console.log(e);
	// 	});
	// }


	



});

/** 
function which convert conll raw input into arborator json tree object to graphically visualize them.
The format is one word per line, and tabulation separated values (not spaces !)
@ deprecated
@see drawConll function beacause this function uses a string to parse with a jsonObject. Not very optimized.
**/
function drawConllTag(elementConll,num) {
	var lines = elementConll.innerHTML.split('\n');
	var arrayWords = [];

	// iterate over each word to add its json object to the array
	for(var i = 0;i < lines.length;i++){
		var cols = lines[i].split('\t');
		var wordJson = '"'+cols[0]+'": '
		+ '{"cat": "'+cols[3]+'", '
		+ '"gov": {"'+cols[6]+'": "'+cols[7]+'"}, "index": "'+cols[0]+'", '
		+ '"lemma": "'+cols[2]+'", "lexid": "'+cols[0]+'", "nb": "'+cols[0]+'", "t": "'+cols[1]+'", "token": "'+cols[1]+'"}';
		arrayWords.push(wordJson);
	}

	// join the word json objects all together as one
	var jsonObj = "{" + arrayWords.join() + "}";
	elementConll.innerHTML = jsonObj;

	// inject the holder div and intialize the script content
	elementConll.innerHTML = '<div id="holder'+num+'" class="svgholder" style="background-color: white; overflow: auto"> </div>';
	var scriptContent =  '$(document).ready(function(){draw("holder'+num+'",'
		+ jsonObj 
		+ ');});';

	// script injection
	var script= document.createElement('script');
	script.type= 'text/javascript';
	script.text = scriptContent;
	elementConll.appendChild(script);
}

/**
execute a script even after page load
**/
function execScript(scriptContent){
	var body = document.getElementsByTagName("body");
	// script injection
	var script= document.createElement('script');
	script.type= 'text/javascript';
	script.text = scriptContent;
	body[0].appendChild(script);
}


/** function which uses justDrawConll.js by Kim Gerdes to display graphical trees.
Retrieves the conll tag content, create two additionnal div into it, then replace them by a tree
Allows to draw different formats
This is the classic one with does not load new animation on element, but is important for starter
**/
function drawConll1(elementConll,num) {
	// put the content in a variable
	var content = elementConll.innerHTML;

	//check if an anchor already exist in the url and split
	var url = window.location.href;
	var res = url.split("#",1); 


	// inject the holder div and sentence div
	elementConll.innerHTML = '<div class="wow fadeInLeft" >'//data-wow-delay="0.5s">'
	+ '<form>  <div class="input-group"> <label id="copy-input'+num+'" for="#copy-button'+num+'" style="font-size: 12;">'+res+'#tree'+num+'</label> '
	+ ' <span class="input-group-btn"> <button type="button" class="btn btn-default opacity-hover" onclick="exporto('+(num-1)+');" data-toggle="tooltip" data-placement="button"  title="Save the tree to SVG file" ><img src="../img/icons/download.svg"/></button>'
	+ ' <button class="btn btn-default opacity-hover" type="button" id="copy-button'+num+'" data-clipboard-text="'+res+'#tree'+num+'" data-toggle="tooltip" data-placement="button"  title="Copy to Clipboard"> <img src="../img/icons/clipboard.svg"/> </button> </span> </div> </form>'
	+ '<div id="tree'+num+'"><div id="sentence'+num+'" class="sentences"></div>'
	+ '<div id="holder'+num+'" class="svgholder" style="background-color: white; overflow: auto"> </div></div>';

	data=conllNodesToTree(content);
	$("#sentence"+num).html( data["sentence"] );

	draw("holder"+num,data["tree"]);
}

/** function which uses justDrawConll.js by Kim Gerdes to display graphical trees.
Retrieves the conll tag content, create two additionnal div into it, then replace them by a tree
Allows to draw different formats
Loads new animations for elements
**/
function drawConll(elementConll,num) {
	// put the content in a variable
	var content = elementConll.innerHTML;

	//check if an anchor already exist in the url and split
	var url = window.location.href;
	var res = url.split("#",1); 

	//get the number of max sent to display it
	var list = document.getElementsByTagName("conll");
	var nbMaxSent = list.length;

	div = document.createElement('div');
	// div.id = 'newdiv'+num;
	div.innerHTML = '<form>  <div class="input-group"> <span class="input-group-btn"> <button class="btn btn-success" style="border-radius:50%" type="button" id="num-button'+num+'"  data-toggle="tooltip" data-placement="button"  title="This sentence\'s number"> <strong>'+num+' / '+nbMaxSent+'</strong> </button> </span> <label id="copy-input'+num+'" for="#copy-button'+num+'">'+res+'#tree'+num+'</label>  <span class="input-group-btn"> <button type="button" class="btn btn-default opacity-hover" onclick="exporto('+(num-1)+');" data-toggle="tooltip" data-placement="button"  title="Save the tree to SVG file" ><img src="../img/icons/download.svg"/></button> <button class="btn btn-default opacity-hover" type="button" id="copy-button'+num+'" data-clipboard-text="'+res+'#tree'+num+'" data-toggle="tooltip" data-placement="button"  title="Copy to Clipboard"> <img src="../img/icons/clipboard.svg"/> </button> </span> </div> </form>'
		+ '<div id="tree'+num+'"><div id="sentence'+num+'" class="sentences"></div>'
		+ '<div id="holder'+num+'" class="svgholder" style="background-color: white; overflow: auto"> </div>';
		div.className = "new";
	elementConll.appendChild(div);

		data=conllNodesToTree(content);
		$("#sentence"+num).html( data["sentence"] );

		draw("holder"+num,data["tree"]);
	    div.className = 'fade';

}


function clipboard(num){
	// Initialize the tooltip.
	$('#copy-button'+num).tooltip();

  // When the copy button is clicked, select the value of the text box, attempt
  // to execute the copy command, and trigger event to update tooltip message
  // to indicate whether the text was successfully copied.
  $('#copy-button'+num).bind('click', function() {
  	var input = document.querySelector('#copy-input'+num);
  	input.setSelectionRange(0, input.value.length + 1);
  	try {
  		var success = document.execCommand('copy');
  		if (success) {
  			$('#copy-button'+num).trigger('copied', ['Copied!']);
  		} else {
  			$('#copy-button'+num).trigger('copied', ['Copy with Ctrl-c']);
  		}
  	} catch (err) {
  		$('#copy-button'+num).trigger('copied', ['Copy with Ctrl-c']);
  	}
  });

  // Handler for updating the tooltip message.
  $('#copy-button'+num).bind('copied', function(event, message) {
  	$(this).attr('title', message)
  	.tooltip('fixTitle')
  	.tooltip('show')
  	.attr('title', "Copy to Clipboard")
  	.tooltip('fixTitle');
  });
}


/**
function to show a snackbar
**/
function myFunction() {
    // Get the snackbar DIV
    var x = document.getElementById("snackbar");

    // Add the "show" class to DIV
    x.className = "show";

    // After 3 seconds, remove the show class from DIV
    setTimeout(function(){ x.className = x.className.replace("show", ""); }, 3000);
}

/*
the background task to directly load every trees, show the progress, and do not let the user access the trees until they are fully loaded
*/
function loadAllTrees(numToProcess) {
    //var pos = 0;
    var list = document.getElementsByTagName("conll");
    var body = document.body;
	body.className += " hiddenoverflow";
	document.getElementById('loading').style.display = 'block';
	var stop = false;

    // This is run once for every numToProcess items.
    function iteration() {

        // Calculate last position.
        var j = Math.min(index + numToProcess, list.length);
        // Start at current position and loop to last position.
        for (var i = index; i < j; i++) {
   //          // processItem(items, i);
            
            drawConll(list[i],i+1);
			var script = 'clipboard('+(i+1)+');';
			// execScript(script);
			var clipboard = new Clipboard('.btn');
			clipboard.on('success', function(e) {
				// console.log(e.textw);
				myFunction()
			});
			clipboard.on('error', function(e) {
				console.log(e);
			});
			// update the current content
			var percentProgress = (i/list.length)*100;
			document.getElementById("loading-progress-bar").innerHTML = ""
			//+ "<h1 class='overlay-content' style='color:white'>"+i+" / "+list.length+"</h1>"
			// + "<div id='loading-progress-bar' class='progress overlay-content' >" 
			+ "<div class='progress-bar progress-bar-success progress-bar-striped active' role='progressbar' aria-valuenow='"+percentProgress+"'  aria-valuemin='0' aria-valuemax='100' style='width:"+percentProgress+"%''>"
			+ " <span style='font-size:22px;'>"+Math.floor(percentProgress)+"% </span>   </div>"
			// + </div>
			//+ "<div  class='overlay-content' > <button type='button' class='btn btn-danger' id='loaderstop'>Stop!</button>  </div>"
				;
				//style=' position: fixed; right: 65px; bottom: 50px;'
        }
        // Increment current position.
        index += numToProcess;
        console.log(index);
        // Only continue if there are more items to process.
        if (index < list.length && stop == false)
            setTimeout(iteration, 10); // Wait 10 ms to let the UI update.

        if (index == list.length){
        	body.className = "";
        	document.getElementById('loading').style.display = 'none';
        }
         $('#loaderstop').click(function(){
        	var list = document.getElementsByTagName("conll");
        	stop = true;
        	body.className = "";
        	document.getElementById('loading').style.display = 'none';
        	return;
    });

    }
   
    iteration();


}


/*
the background task for loading trees while leaving the user control of the interface
*/
function loadTreesBackground(numToProcess) {
    // var pos = 0;
    var list = document.getElementsByTagName("conll");
    // This is run once for every numToProcess items.
    function iteration() {

        // Calculate last position.
        var j = Math.min(index + numToProcess, list.length);
        // Start at current position and loop to last position.
        for (var i = index; i < j; i++) {
            
            drawConll(list[i],i+1);
			var script = 'clipboard('+(i+1)+');';
			// execScript(script);
			var clipboard = new Clipboard('.btn');
			clipboard.on('success', function(e) {
				// console.log(e.textw);
				myFunction()
			});
			clipboard.on('error', function(e) {
				console.log(e);
			});
        }
        // Increment current position.
        index += numToProcess;
        console.log(index);
        // Only continue if there are more items to process.
        if (index < list.length && autoload == true)
            setTimeout(iteration, 1500); // Wait 10 ms to let the UI update.
    }
    iteration();
}


var inactivityTime = function () {
    var t;
    window.onload = resetTimer;
    document.onmousemove = resetTimer;
    document.onkeypress = resetTimer;

    function load() {
        drawConll(list[i],i+1);
			var script = 'clipboard('+(i+1)+');';
			// execScript(script);
			var clipboard = new Clipboard('.btn');
			clipboard.on('success', function(e) {
				// console.log(e.textw);
				myFunction()
			});
			clipboard.on('error', function(e) {
				console.log(e);
			});
    }

    function resetTimer() {
        clearTimeout(t);
        t = setTimeout(load, 3000)
        // 1000 milisec = 1 sec
    }
};

function resumeload(){
	autoload = true;
	loadTreesBackground(1);
}

function stopload(){
	autoload = false;
}

/* save svg files */

var exportSVG = function(svg, name) {
  // first create a clone of our svg node so we don't mess the original one
  var clone = svg.cloneNode(true);
  // parse the styles
  parseStyles(clone);

  // create a doctype
  var svgDocType = document.implementation.createDocumentType('svg', "-//W3C//DTD SVG 1.1//EN", "http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd");
  // a fresh svg document
  var svgDoc = document.implementation.createDocument('http://www.w3.org/2000/svg', 'svg', svgDocType);
  // replace the documentElement with our clone 
  svgDoc.replaceChild(clone, svgDoc.documentElement);
  // get the data
  var svgData = (new XMLSerializer()).serializeToString(svgDoc);

  // now you've got your svg data, the following will depend on how you want to download it
  // e.g yo could make a Blob of it for FileSaver.js
  
  var blob = new Blob([svgData.replace(/></g, '>\n\r<')]);
  saveAs(blob, name+ '.svg');

  /*canvas_options_form = $("canvas-options");
  canvas_filename = $("canvas-filename")
  canvas_options_form.addEventListener("submit", function(event) {
	event.preventDefault();
	svg.toBlobHD(function(blob) {
		saveAs(
			  blob
			, (canvas_filename.value || canvas_filename.placeholder) + ".png"
		);
	}, "image/png");
}, false);*/
  
  // here I'll just make a simple a with download attribute

  /*
  var a = document.createElement('a');
  a.href = 'data:image/svg+xml; charset=utf8, ' + encodeURIComponent(svgData.replace(/></g, '>\n\r<'));
  a.download = 'myAwesomeSVG.svg';
  a.innerHTML = 'download the svg file';
  document.body.appendChild(a);
  */

};

var parseStyles = function(svg) {
  var styleSheets = [];
  var i;
  // get the stylesheets of the document (ownerDocument in case svg is in <iframe> or <object>)
  var docStyles = svg.ownerDocument.styleSheets;

  // transform the live StyleSheetList to an array to avoid endless loop
  for (i = 0; i < docStyles.length; i++) {
    styleSheets.push(docStyles[i]);
  }

  if (!styleSheets.length) {
    return;
  }

  var defs = svg.querySelector('defs') || document.createElementNS('http://www.w3.org/2000/svg', 'defs');
  if (!defs.parentNode) {
    svg.insertBefore(defs, svg.firstElementChild);
  }
  svg.matches = svg.matches || svg.webkitMatchesSelector || svg.mozMatchesSelector || svg.msMatchesSelector || svg.oMatchesSelector;


  // iterate through all document's stylesheets
  for (i = 0; i < styleSheets.length; i++) {
    var currentStyle = styleSheets[i]

    var rules;
    try {
      rules = currentStyle.cssRules;
    } catch (e) {
      continue;
    }
    // create a new style element
    var style = document.createElement('style');
    // some stylesheets can't be accessed and will throw a security error
    var l = rules && rules.length;
    // iterate through each cssRules of this stylesheet
    for (var j = 0; j < l; j++) {
      // get the selector of this cssRules
      var selector = rules[j].selectorText;
      // probably an external stylesheet we can't access
      if (!selector) {
        continue;
      }

      // is it our svg node or one of its children ?
      if ((svg.matches && svg.matches(selector)) || svg.querySelector(selector)) {

        var cssText = rules[j].cssText;
        // append it to our <style> node
        style.innerHTML += cssText + '\n';
      }
    }
    // if we got some rules
    if (style.innerHTML) {
      // append the style node to the clone's defs
      defs.appendChild(style);
    }
  }

};

function exporto(num) {
	var sentenceName = $('#sentence'+(num+1)).text();
	exportSVG($('svg')[num], sentenceName);
}

function exportall() {
	var svglist = document.getElementsByTagName("svg");
	console.log(svglist.length)
	
	for(var i =  0; i < svglist.length ; i++){
		var sentenceName = $('#sentence'+(i+1)).text();
		var sentenceName = sentenceName.substring(0, 150);
		exportSVG(svglist[i], sentenceName);
	}
}