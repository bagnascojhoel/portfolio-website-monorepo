<script lang="ts">
  export let isOpen: boolean = false;
</script>

{#if !isOpen}
  <div class="p-4 py-6 bg-on-primary">
    <div class="h-4 bg-slate-200 rounded animate-pulse" />
  </div>
{:else}
  <div class="p-4 py-6 bg-on-primary">
    <div class="animate-pulse">
      <div class="h-4 bg-slate-200 rounded" />
      <div class="my-6 grid grid-cols-5 gap-2">
        <div class="h-3 bg-slate-200 rounded " />
        <div class="h-3 bg-slate-200 rounded " />
        <div class="h-3 bg-slate-200 rounded " />
        <div class="h-3 bg-slate-200 rounded " />
      </div>
      <div class="h-24 bg-slate-200 rounded" />
      <div class="mt-6 grid grid-cols-3 gap-4">
        <div class="h-4 bg-slate-200 rounded" />
        <div class="h-4 bg-slate-200 rounded" />
      </div>
    </div>
  </div>
{/if}
