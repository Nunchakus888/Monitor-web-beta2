
$("g").on("click","g",function(){
	var that = this;
	setTimeout(function(){
//		debugger
		var left = $(that).find("text").offset().left;
		var top = $(that).find("text").offset().top;
		console.log(top + "~~~~" + left);
	},500);
		
});

$("button").click(function(){
	json = {
 "name": "sumacope",
 "children": [
  {
   "name": "cdh",
   "value":"123",
   "children": []
  },
  {
   "name": "animate",
   "children": []
  },
  {
   "name": "data",
   "children": []
  },
  {
   "name": "display",
   "children": []
  },
  {
   "name": "flex",
   "children": [
    {"name": "FlareVis"}
   ]
  },
  {
   "name": "physics",
   "children": []
  },
  {
   "name": "query",
   "children": []
  },
  {
   "name": "scale",
   "children": []
  },
  {
   "name": "util",
   "children": []
  },
  {
   "name": "vis",
   "children": []
  }
 ]
}
	root = json;
		root.x0 = h / 2;
		root.y0 = 0;

		function toggleAll(d) {
			if(d.children) {
				d.children.forEach(toggleAll);
//				toggle(d);
			}
		}

		// Initialize the display to show a few nodes.
		root.children.forEach(toggleAll);
		//toggle(root.children[0]);
		//toggle(root.children[1].children[2]);

		update(root);
});
