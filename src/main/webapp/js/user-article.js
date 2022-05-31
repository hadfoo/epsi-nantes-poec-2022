var app = new Vue({
	el: '#app',
	data() {
		return {
			article: {
				ingredients: []
			},
			ingredients: [],
			ingredient: {}
		}
	},
	mounted() {
		this.loadArticle();
		this.loadIngredients();
	},
	methods: {
		loadArticle() {
			let url = new URL(window.location);
			let code = url.searchParams.get("code");
			
			axios.get('/user/article?code=' + code)
			.then(response => {
				this.article = response.data.data;
			});
		},
		loadIngredients() {
			axios.get('/public/ingredients')
			.then(response => {
				this.ingredients = response.data.data;
			});
		},
		ajouterIngredient() {
			if (!this.article) {
				return;
			}
			axios.post('/user/article/ingredient' +
				'?article_id=' + this.article.id +
				'&ingredient_id=' + this.ingredient.id)
			.then(response => {
				if (response.data.success) {
					this.article.ingredients.push(this.ingredient);
					this.ingredient = null;
				}
			})
		}
	}
});