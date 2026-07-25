import { Component, debounced, resource, signal } from '@angular/core';
import { LucideSearch } from '@lucide/angular';

@Component({
  selector: 'app-search',
  imports: [LucideSearch],
  templateUrl: './search.html',
  styleUrl: './search.css',
})
export class Search {
  query = signal('');

  debouncedQuery = debounced(this.query, 600);

 
}
